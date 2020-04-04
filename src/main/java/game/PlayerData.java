package game;

import io.netty.channel.Channel;
import protocol.GameProtocol;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {
  public String id; //存档id
  public String name; //名字
  public STATUS status = STATUS.DISCONNECTED; //状态
  public Channel channel;
  public Position position = new Position(); //位置数据
  public RoleData roleData = new RoleData(); //角色数据
  public MapBooth mapBooth; // 摊位数据
  public List<GameProtocol> protocolsQueue = new ArrayList<>(); //协议缓存

  public enum STATUS {
    DISCONNECTED,
    NORMAL
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
      if(this.mapId != position.mapId || this.x != position.x || this.y != position.y || this.direction != position.direction || this.rad != position.rad || this.status != position.status){
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

    public boolean isNeedSync(RoleData roleData){
      if(this.dataId != roleData.dataId || this.weaponId != roleData.weaponId){
        return true;
      }
      if(!color.equals(roleData.color)){
        return true;
      }
      return false;
    }
  }



}

