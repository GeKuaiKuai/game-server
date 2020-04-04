package protocol;

public class CCreateBooth extends GameProtocol{
  public String name;

  @Override
  public String toString() {
    return "CCreateBooth{" +
        "name='" + name + '\'' +
        '}';
  }
}
