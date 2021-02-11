package game.Service;

import game.MapBooth;
import game.PlayerData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import protocol.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class GameMapRsyncLoop extends GameFrameLoop {
  private static Logger logger = LogManager.getLogger(GameMapRsyncLoop.class);
  protected long EXE_PERIOD = 100;
  private final static int MAX_ROW = 40;
  private final static int MAX_COL = 40;
  private final static int OFF = 1; // 在格子距离为1的范围内同步
  private final static int MAX_COMMENTS = 1;

  private static List<PositionOffset> positionOffsets;

  private static class PositionOffset {
    int x;
    int y;
    PositionOffset(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

  static {
    positionOffsets = new ArrayList<>();
    positionOffsets.add(new PositionOffset(0, 0));
    positionOffsets.add(new PositionOffset(-1, -1));
    positionOffsets.add(new PositionOffset(-1, 0));
    positionOffsets.add(new PositionOffset(0, -1));
    positionOffsets.add(new PositionOffset(-1, 1));
    positionOffsets.add(new PositionOffset(0, 1));
    positionOffsets.add(new PositionOffset(1, 1));
    positionOffsets.add(new PositionOffset(1, -1));
    positionOffsets.add(new PositionOffset(1, 0));
  }


  private Map<String, PlayerMapData> playersData = new ConcurrentHashMap<>();
  private Map<Integer, MapRect[][]> mapDatas = new ConcurrentHashMap<>(); // mapId:value

  public void addNewOnlinePlayer(PlayerData playerData) {
    var mapData = new PlayerMapData();
    mapData.id = playerData.id;
    mapData.data = playerData;
    playersData.put(playerData.id, mapData);
  }

  public void removeOnlinePlayer(PlayerData playerData) {
    var playerMapData = playersData.get(playerData.id);
    if(playerMapData == null){
      return;
    }

    if(playerMapData.mapBooth != null){
      closeBooth(playerMapData.data);
    }

    playersData.remove(playerData.id); //从大表删除
    var mapRect = playerMapData.mapRect;
    if(mapRect != null){
      mapRect.playerMapData.remove(playerMapData); //从rect里删除
    }
    var notifyOther = new NotifyPlayerLeave();
    notifyOther.id = playerMapData.id;
    var list = getNearbyPlayers(playerMapData.mapRect);

    list.forEach(player -> {
      if(player.data.channel != null){
        player.data.channel.write(notifyOther);
      }else{
        logger.error("channel is not exist when notify leave. id="+player.id);
        player.data.status = PlayerData.STATUS.DISCONNECTED;
      }
      player.sequenceList.remove(playerMapData.id);
    });

  }

  // 同步位置
  public void syncPositionSequence(PlayerData playerData) {
    var data = playersData.get(playerData.id);
    data.rsyncSequence[1] += 1;
    data.rsyncSequence[1] %= 65536;
  }

  // 同步信息
  public void syncDataSequence(PlayerData playerData) {
    var data = playersData.get(playerData.id);
    data.rsyncSequence[0] += 1;
    data.rsyncSequence[0] %= 65536;
  }

  // 软同步信息
  public void syncDataSequenceSoft(PlayerData playerData) {
    var data = playersData.get(playerData.id);
    if(data.rsyncSequence[0] == -1){
      data.rsyncSequence[0] = 0;
    }
  }

  // 创建摊位
  public void createBooth(PlayerData playerData){
    var data = playersData.get(playerData.id);
    if(data.mapBooth != null){
      return;
    }
    data.mapBooth = new MapBooth();
    data.mapBooth.saveId = playerData.id;
    syncDataSequence(playerData);
  }

  // 关闭摊位
  public void closeBooth(PlayerData playerData){
    var data = playersData.get(playerData.id);
    if(data.mapBooth == null){
      return;
    }
    for(var player:data.mapBooth.getVisitors()){
      var notify = new SCloseBoothWindow();
      notify.id = playerData.id;
      if(player.isOnline() && player.channel!=null){
        player.channel.write(notify);
      }else{
        logger.error("channel is not exist when notify booth close. id="+player.id);
      }
    }
    data.mapBooth = null;
    syncDataSequence(playerData);
  }

  // 获取摊位
  public MapBooth getBooth(PlayerData playerData){
    var data = playersData.get(playerData.id);
    if(data == null){
      return null;
    }else{
      return data.mapBooth;
    }
  }


  // 同步聊天
  public void syncComment(PlayerData playerData, Comment comment) {
    var mapData = playersData.get(playerData.id);
    if(mapData == null){
      return;
    }
    if(mapData.comments.size() == MAX_COMMENTS){
      return;
    }
    try {
      mapData.comments.put(comment);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 地图玩家数据
   */
  private static class PlayerMapData {
    // 关键引用
    String id;
    PlayerData data;

    // 位置数据
    int rx;
    int ry;
    PlayerData.Position position;
    // 普通数据
    PlayerData.RoleData roleData;
    //聊天数据
    LinkedBlockingQueue<Comment> comments = new LinkedBlockingQueue<Comment>();

    //摊位数据
    MapBooth mapBooth;

    // 同步标识
    int[] loopRsyncSequence = new int[]{-1,-1}; // 0 数据 1 位置
    int[] rsyncSequence = new int[]{-1,-1}; // 0 数据 1 位置
    Map<String, int[]> sequenceList = new HashMap<>();
    MapRect mapRect;

    // 是否需要移动大区域
    boolean isNeedMove(){
      if(position == null){
        return false;
      }
      if(mapRect == null){
        return true;
      }
      return mapRect.rx != rx || mapRect.ry != ry || mapRect.mapId != position.mapId;
    }

  }

  /**
   * 玩家发言
   */
  public static class Comment{
    public COMMENT_TYPE type;
    public String content;
  }

  /**
   * 发言类型
   */
  public enum COMMENT_TYPE{
    NEARBY,
    WORLD
  }

  /**
   * 地图区块数据
   */
  private static class MapRect{
    int rx;
    int ry;
    int mapId;
    Set<PlayerMapData> playerMapData;


    boolean isVisible(MapRect rect){
      if(rect == null){
        return false;
      }
      if(rect.mapId != mapId){
        return false;
      }else{
        return Math.sqrt(Math.pow(rx-rect.rx,2) + Math.pow(ry-rect.ry, 2)) <= Math.sqrt(OFF*2);
      }
    }
  }

  private int getRectX(int x) {
    return x / 800;
  }

  private int getRectY(int y) {
    return y / 600;
  }

  private void updateAllPlayerPosition() {

    for (var kv : playersData.entrySet()) {
      updatePlayerPosition(kv.getValue());
    }

    for (var kv : playersData.entrySet()) {
      if(kv.getValue().data.channel != null){
        kv.getValue().data.channel.flush();
      }else{
        logger.error("channel is not exist when flush channel. id="+kv.getValue().id);
      }

    }
  }

  private void updatePlayerPosition(PlayerMapData data) {
    // 处理离线玩家
    if(offlineSync(data)){
      return;
    }
    rsyncData(data); // 从GameData里同步信息
    movePlayerRect(data);
    broadRoleInfo(data); // 广播信息
    broadPosition(data); // 广播位置
    broadComment(data);
  }

  private void broadComment(PlayerMapData data){
    while(data.comments.size() > 0){
      var comment = data.comments.poll();
      var notify = new NotifyComment();
      notify.id = data.id;
      notify.name = data.data.name;
      notify.type = comment.type.ordinal();
      notify.content = comment.content;
      if(comment.type == COMMENT_TYPE.NEARBY){
        broadMsg(data, notify);
      }else if(comment.type == COMMENT_TYPE.WORLD){
        broadMsgToAllOnline(notify);
      }
    }
  }

  // 获取现在的位置应该所在的区域
  private MapRect getCurrentRect(PlayerMapData data){
    var mapRectArr = createIfAbsent(data.position.mapId);
    if(mapRectArr == null){
      return null;
    }
    return mapRectArr[data.ry][data.rx];
  }

  // 移动角色区域
  private void movePlayerRect(PlayerMapData data){
    if(data.isNeedMove()){
      var newRect = getCurrentRect(data);
      // 通知旧区域玩家删除
      var oldRect = data.mapRect;
      if(oldRect != null){
        int rx = oldRect.rx;
        int ry = oldRect.ry;
        var mapRectArr = mapDatas.get(oldRect.mapId);
        var notifyOther = new NotifyPlayerLeave();
        notifyOther.id = data.id;
        for (var off : positionOffsets) {
          int offX = rx + off.x;
          int offY = ry + off.y;
          if (offX < 0 || offX >= MAX_COL || offY < 0 || offY >= MAX_ROW) {
            continue;
          }
          var mapRect = mapRectArr[offY][offX];
          if(!mapRect.isVisible(newRect)){
            for(var playerData:mapRect.playerMapData){
              // 通知客户端删除
              if(playerData.data.channel != null){
                playerData.data.channel.write(notifyOther);
              }else{
                logger.error("channel is not exist when notify leave. id="+data.data.id);
                playerData.data.status = PlayerData.STATUS.DISCONNECTED;
              }
              var notifyMe = new NotifyPlayerLeave();
              notifyMe.id = playerData.id;
              if(data.data.channel != null){
                data.data.channel.write(notifyMe);
              }else{
                logger.error("channel is not exist when move. id="+data.data.id);
              }
              // 周围玩家删除本玩家编号
              playerData.sequenceList.remove(data.id);
              // 本玩家删除周围玩家编号
              data.sequenceList.remove(playerData.id);
            }
          }
        }
        oldRect.playerMapData.remove(data);
      }
      // 进行移动
      if(newRect!=null){
        newRect.playerMapData.add(data);
      }
      data.mapRect = newRect;
    }
  }

  private boolean offlineSync(PlayerMapData playerMapData){
    if(!playerMapData.data.isOnline() || playerMapData.data.channel == null){
      playerMapData.data.status = PlayerData.STATUS.DISCONNECTED;
      removeOnlinePlayer(playerMapData.data);
      return true;
    }
    return false;
  }

  private void rsyncData(PlayerMapData data){
    // 同步数据
    if(data.loopRsyncSequence[0] != data.rsyncSequence[0]){
      data.roleData = data.data.roleData;
      data.loopRsyncSequence[0] = data.rsyncSequence[0];
    }
    // 同步位置数据
    if(data.loopRsyncSequence[1] != data.rsyncSequence[1]){
      data.position = data.data.position;
      data.rx = getRectX(data.position.x);
      data.ry = getRectY(data.position.y);
      data.loopRsyncSequence[1] = data.rsyncSequence[1];
    }
  }

  private void broadRoleInfo(PlayerMapData data) {
    if(data.roleData == null){
      return;
    }
    var msg = new NotifyRoleInfo();
    msg.id = data.id;
    msg.name = data.data.name;
    msg.dataId = data.roleData.dataId;
    msg.weaponId = data.roleData.weaponId;
    msg.color = data.roleData.color;
    msg.battle = data.roleData.battle;
    if(data.mapBooth != null){
      msg.boothName = data.mapBooth.name;
    }
    broadMsg(data, 0, msg);
  }

  private void broadPosition(PlayerMapData data) {
    if(data.position == null){
      return;
    }
    var msg = new NotifyPlayerPosition();
    msg.id = data.id;
    msg.status = data.position.status;
    msg.x = data.position.x;
    msg.y = data.position.y;
    msg.direction = data.position.direction;
    msg.rad = data.position.rad;
    broadMsg(data, 1, msg);
  }


  /**
   * 根据序号选择发送消息
   */
  private void broadMsg(PlayerMapData sender, int kind, GameProtocol msg) {
     var list = getNearbyPlayers(sender.mapRect);
     list.forEach(player -> sendMsg(sender, player, kind, msg));
  }

  /**
   * 直接发送
   */
  private void broadMsg(PlayerMapData sender, GameProtocol msg){
    var list = getNearbyPlayers(sender.mapRect);
    list.forEach(playerMapData -> {
      if(playerMapData.data.channel != null){
        playerMapData.data.channel.write(msg);
      }else{
        logger.error("channel is not exist when direct broadMsg. id="+playerMapData.data.id);
      }
    });
  }

  /**
   * 给全部在线玩家同步
   */
  private void broadMsgToAllOnline(GameProtocol msg){
    playersData.forEach((key, value) -> {
      if(value.data.channel == null){
        value.data.status = PlayerData.STATUS.DISCONNECTED;
      }else{
        value.data.channel.write(msg);
      }
    });
  }

  private void sendMsg(PlayerMapData sender, PlayerMapData receiver, int kind, GameProtocol msg){
    if(sender == receiver){
      return;
    }

    if(receiver.data.channel == null){
      logger.error("channel is not exist when broadMsg. id="+receiver.data.id);
      return;
    }
    var sequences = receiver.sequenceList.computeIfAbsent(sender.id, k -> new int[]{-1, -1});
    if(sequences[kind] != sender.rsyncSequence[kind]){
      receiver.data.channel.write(msg);
      sequences[kind] =  sender.rsyncSequence[kind];
    }
  }

  /**
   * 获取MapRect附近的玩家集合
   */
  private List<PlayerMapData> getNearbyPlayers(MapRect mapRect){
    List<PlayerMapData> res = new ArrayList<>();
    if(mapRect == null){
      return res;
    }
    int rx = mapRect.rx;
    int ry = mapRect.ry;
    var mapRectArr = mapDatas.get(mapRect.mapId);
    for (var off : positionOffsets) {
      int offX = rx + off.x;
      int offY = ry + off.y;
      if (offX < 0 || offX >= MAX_COL || offY < 0 || offY >= MAX_ROW) {
        continue;
      }
      var otherRect = mapRectArr[offY][offX];
      res.addAll(otherRect.playerMapData);
    }
    return res;
  }

  // 创建地图数据结构
  private MapRect[][] createIfAbsent(int mapId) {
    if(mapId == 0){
      return null;
    }
    if (mapDatas.get(mapId) == null) {
      MapRect[][] mapRect = new MapRect[MAX_ROW][MAX_COL];
      for (int i = 0; i < MAX_ROW; i++) {
        for (int j = 0; j < MAX_COL; j++) {
          mapRect[i][j] = new MapRect();
          mapRect[i][j].rx = j;
          mapRect[i][j].ry = i;
          mapRect[i][j].mapId = mapId;
          mapRect[i][j].playerMapData = new HashSet<>();
        }
      }
      mapDatas.put(mapId, mapRect);
    }
    return mapDatas.get(mapId);
  }

  @Override
  protected long getPeriod() {
    return EXE_PERIOD;
  }

  @Override
  protected void work() {
    updateAllPlayerPosition();
  }

}
