package game;

import java.util.ArrayList;
import java.util.List;

public class BattleInfo {
  public final Object lock = new Object();
  public String name; //战斗名称
  public List<BattleComment> commentList = new ArrayList<>();

  public void insertComment(BattleComment comment){
    synchronized (lock){
      commentList.add(comment);
    }
  }

  public static class BattleComment{
    public int time; //单位s
    public String name;
    public String content;
  }
}
