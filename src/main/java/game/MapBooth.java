package game;

import protocol.NotifyBoothUpdate;
import protocol.SOpenBoothWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapBooth {
  private final Object lock = new Object();
  public String saveId;
  public String name = "杂货摊位";
  private List<Item> items = new ArrayList<>();
  private List<Pet> pets = new ArrayList<>();
  private List<PlayerData> visitors = new ArrayList<>();
  private Map<String, String> productData = new HashMap<>(); // 武器、装备、召唤兽数据 lua
  private int productIdCount = 0;

  public static class Item{
    public String productId; // 随机生成，不重复
    public int count;
    public int price;
    public String itemData; // 物品信息 普通物品会用
  }

  public static class Pet{
    public String productId; //随机生成，不重复
    public int price;
  }

  public List<Item> getItems(){
    return items;
  }

  public List<Pet> getPets() {
    return pets;
  }

  public List<PlayerData> getVisitors() {
    return visitors;
  }

  public void addVisitors(PlayerData gameData){
    synchronized (lock){
      visitors.add(gameData);
    }
  }

  public void removeVisitors(PlayerData gameData){
    synchronized (lock){
      visitors.remove(gameData);
    }
  }

  public Map<String, String> getProductData() {
    return productData;
  }

  /**
   * 通知货摊变化
   */
  public void broadBoothUpdate(String filterId){
    var notify = new NotifyBoothUpdate();
    notify.id = saveId;
    notify.name = name;
    notify.items = items;
    notify.pets = pets;
    notify.productData = productData;
    List<PlayerData> removeList = new ArrayList<>();
    visitors.forEach(visitor -> {
      if(filterId==null || !filterId.equals(visitor.id)){
        if(visitor.isOnline()){
          visitor.channel.write(notify);
        }else{
          removeList.add(visitor);
        }
      }
    }
    );
    for (var visitor:removeList){
      removeVisitors(visitor);
    }
  }

  /**
   * 货品上架
   * @param count 道具数目
   * @param itemData (背包数组),subData(武器、装备数据)
   * @return product id
   */
  public String addItem(int price, int count, String itemData, String subData){
    Item item = new Item();
    item.count = count;
    item.price = price;
    item.itemData = itemData;
    synchronized (lock){
      item.productId = String.valueOf(productIdCount++);
      items.add(item);
      if(subData != null){
        productData.put(item.productId, subData);
      }
    }
    return item.productId;
  }

  public String addPet(int price, String subData){
    Pet pet = new Pet();
    pet.price = price;
    synchronized (lock){
      pet.productId = String.valueOf(productIdCount++);
      pets.add(pet);
      productData.put(pet.productId, subData);
    }
    return pet.productId;
  }

  public void removeItem(String productId){
    items.removeIf(item -> item.productId.equals(productId));
    productData.remove(productId);
  }

  public void removePet(String productId){
    pets.removeIf(pet -> pet.productId.equals(productId));
    productData.remove(productId);
  }

  public boolean buyItem(String productId, int count){
    for(var item:items){
      if(item.productId.equals(productId)){
        if(count > item.count){
          return false;
        }else {
          item.count = item.count - count;
          if(item.count == 0){
            removeItem(productId);
          }
          return true;
        }
      }
    }
    //todo:bb
    for(var pet:pets){
      if(pet.productId.equals(productId)){
        removePet(productId);
        return true;
      }
    }
    return false;
  }


  public SOpenBoothWindow openBoothWindow(){
    var res = new SOpenBoothWindow();
    res.name = name;
    res.items = items;
    res.pets = pets;
    res.productData = productData;
    return res;
  }

  public void closeBoothWindow(){

  }


}


