package game;

import io.netty.channel.Channel;
import protocol.GameProtocol;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {
  public String id; //存档id
  public String name; //名字
  public String qq;
  public int lastSaveTime; // 上次存档时间
  public List<String> luaQueue = new ArrayList<>(); //待执行的lua代码

  public STATUS status = STATUS.DISCONNECTED; //状态
  public Channel channel;
  public Position position = new Position(); //位置数据
  public RoleData roleData = new RoleData(); //角色数据
  public List<GameProtocol> protocolsQueue = new ArrayList<>(); //协议缓存，记录发送失败的协议
  public boolean over = false;
  public boolean isModify = false;

  public enum STATUS {
    DISCONNECTED,
    NORMAL
  }

  public static class PlayerDataStore{
    public String id; //存档id
    public String name; //名字
    public String qq;
    public int lastSaveTime; // 上次存档时间
    public List<String> luaQueue = new ArrayList<>(); //待执行的lua代码
    public boolean over;
    public boolean isModify;
  }

  public PlayerDataStore getDataStore(){
    PlayerDataStore res = new PlayerDataStore();
    res.id = id;
    res.name = name;
    res.qq = qq;
    res.lastSaveTime = lastSaveTime;
    res.luaQueue = luaQueue;
    res.over = over;
    res.isModify = isModify;
    return res;
  }

  public PlayerData(){

  }

  public PlayerData(PlayerDataStore store){
    id = store.id;
    name = store.name;
    qq = store.qq;
    lastSaveTime = store.lastSaveTime;
    luaQueue = store.luaQueue;
  }

  public boolean isOnline() {
    return status == STATUS.NORMAL;
  }

  public static class Position{
    public int mapId;
    public int x;
    public int y;
    public int direction;
    public double rad; //弧度
    public int status;


    public boolean isNeedSync(Position position){
      if(this.mapId != position.mapId || this.x != position.x || this.y != position.y || this.direction != position.direction || this.rad != position.rad || this.status != position.status ){
        return true;
      }else{
        return false;
      }
    }
  }

  public static class RoleData{
    public int dataId;
    public int weaponId;
    public String color;
    public boolean battle;

    public boolean isNeedSync(RoleData roleData){
      if(this.dataId != roleData.dataId || this.weaponId != roleData.weaponId || this.battle != roleData.battle){
        return true;
      }
      if(!color.equals(roleData.color)){
        return true;
      }
      return false;
    }
  }







}

