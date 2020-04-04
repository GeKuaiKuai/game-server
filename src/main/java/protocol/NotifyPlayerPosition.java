package protocol;

public class NotifyPlayerPosition extends GameProtocol{
    public String id;
    public int status; // 0 静止,1 移动
    public int direction;
    public double rad; // 弧度
    public int x;
    public int y;
}
