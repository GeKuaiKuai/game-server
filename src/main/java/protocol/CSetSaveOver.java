package protocol;

public class CSetSaveOver extends GameProtocol {
    public String id;
    public boolean over = true;

    @Override
    public String toString() {
        return "CSetSaveOver{" +
                "id='" + id + '\'' +
                ", over=" + over +
                '}';
    }
}
