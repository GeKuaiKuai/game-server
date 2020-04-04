package protocol;

public class NotifyComment extends GameProtocol{
  public String id;
  public String name;
  public int type;
  public String content;

  @Override
  public String toString() {
    return "NotifyComment{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", type=" + type +
        ", content='" + content + '\'' +
        '}';
  }
}
