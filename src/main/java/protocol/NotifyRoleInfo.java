package protocol;

public class NotifyRoleInfo extends GameProtocol {
    public String id;
    public String name;
    public int dataId;
    public int weaponId;
    public String color;

    @Override
    public String toString() {
        return "NotifyRoleInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dataId=" + dataId +
                ", weaponId=" + weaponId +
                ", color='" + color + '\'' +
                '}';
    }
}
