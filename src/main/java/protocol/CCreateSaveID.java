package protocol;

public class CCreateSaveID extends GameProtocol{
    public String name;
    public String qq;

    @Override
    public String toString() {
        return "CCreateSaveID{" +
                "name='" + name + '\'' +
                ", qq='" + qq + '\'' +
                '}';
    }
}
