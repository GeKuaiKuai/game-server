package protocol;

public class CSyncPosition extends GameProtocol{
  public int x;
  public int y;
  public int direction;
  public int mapId;
  public int status;
  public double rad;

  @Override
  public String toString() {
    return "CSyncPosition{" +
            "x=" + x +
            ", y=" + y +
            ", direction=" + direction +
            ", mapId=" + mapId +
            ", status=" + status +
            ", rad=" + rad +
            '}';
  }
}
