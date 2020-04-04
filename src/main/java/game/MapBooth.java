package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapBooth {
  public Object lock = new Object();
  public String name;
  public List<Item> items = new ArrayList<>();
  public List<Pet> pets = new ArrayList<>();
  public List<GameData> visitors = new ArrayList<>();
  public Map<Integer, String> productData = new HashMap<>(); // 武器、装备、召唤兽数据 lua

  public static class Item{
    public int productId; // 随机生成，不重复
    public int id; // id 只有普通物品会用
    public int type; // 类型 0 普通物品 1 武器 2 装备
    public int count;
    public Map<String,Object> extraData; // 额外信息 普通物品会用
  }

  public static class Pet{
    public int productId; //随机生成，不重复
  }

}


