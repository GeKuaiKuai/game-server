package protocol;

import game.MapBooth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotifyBoothUpdate extends GameProtocol{
    public String id;
    public String name;
    public List<MapBooth.Item> items = new ArrayList<>();
    public List<MapBooth.Pet> pets = new ArrayList<>();
    public Map<String, String> productData = new HashMap<>(); // 武器、装备、召唤兽数据 lua

    @Override
    public String toString() {
        return "NotifyBoothUpdate{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", items=" + items +
                ", pets=" + pets +
                ", productData=" + productData +
                '}';
    }
}
