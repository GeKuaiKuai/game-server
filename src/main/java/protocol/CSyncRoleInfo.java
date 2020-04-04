package protocol;

public class CSyncRoleInfo extends GameProtocol {
    public int data_id;
    public int weapon_id;
    public String color;

    @Override
    public String toString() {
        return "CSyncRoleInfo{" +
                "data_id=" + data_id +
                ", weapon_id=" + weapon_id +
                ", color='" + color + '\'' +
                '}';
    }
}
