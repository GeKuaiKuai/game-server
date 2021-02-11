package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameRank {
  private final Object levelLock = new Object();
  private final Object moneyLock = new Object();
  private final Object battleLock = new Object();
  public Map<String, LevelRank> levelRanks = new HashMap<>();
  public Map<String, MoneyRank> moneyRanks = new HashMap<>();
  public Map<String, BattleRank> battleRanks = new HashMap<>();


  public void clearRank(String id){
    synchronized (levelLock){
      levelRanks.remove(id);
    }
    synchronized (moneyLock){
      moneyRanks.remove(id);
    }
    synchronized (battleLock){
      battleRanks.remove(id);
    }
  }

  public void putLevelRank(String id, LevelRank levelRank){
    synchronized (levelLock){
      levelRanks.put(id,levelRank);
    }
  }

  public List<LevelRank> getLevelRankList(){
    List<LevelRank> rankList = new ArrayList<>();
    synchronized (levelLock){
      levelRanks.forEach((k,v)->{
        rankList.add(v);
      });
    }
    return rankList;
  }

  public void putMoneyRank(String id, MoneyRank moneyRank){
    synchronized (moneyLock){
      moneyRanks.put(id,moneyRank);
    }
  }

  public List<MoneyRank> getMoneyRankList(){
    List<MoneyRank> rankList = new ArrayList<>();
    synchronized (moneyLock){
      moneyRanks.forEach((k,v)->{
        rankList.add(v);
      });
    }
    return rankList;
  }

  public void putBattleRank(String id, BattleRank battleRank){
    synchronized (battleLock){
      battleRanks.put(id,battleRank);
    }
  }

  public List<BattleRank> getBattleRankList(){
    List<BattleRank> rankList = new ArrayList<>();
    synchronized (battleLock){
      battleRanks.forEach((k,v)->{
        rankList.add(v);
      });
    }
    return rankList;
  }


  public static class LevelRank implements Comparable<LevelRank>{
    public String name;
    public int count;
    public int level;

    @Override
    public int compareTo(LevelRank levelRank) {
      return levelRank.level - this.level;
    }
  }

  public static class MoneyRank implements Comparable<MoneyRank>{
    public String name;
    public int money;

    public int compareTo(MoneyRank moneyRank) {
      return moneyRank.money - this.money;
    }
  }

  public static class BattleRank implements Comparable<BattleRank>{
    public String name;
    public int battle;

    public int compareTo(BattleRank battleRank) {
      return battleRank.battle - this.battle;
    }
  }


}
