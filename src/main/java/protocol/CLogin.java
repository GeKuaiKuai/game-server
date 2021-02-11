package protocol;

public class CLogin extends GameProtocol{
  public String id;
  public Boolean reconnect=false;
  public int version=0;

  @Override
  public String toString() {
    return "CLogin{" +
            "id='" + id + '\'' +
            ", reconnect=" + reconnect +
            ", version=" + version +
            '}';
  }
}
