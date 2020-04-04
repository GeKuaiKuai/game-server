package game;

import java.util.List;
import java.util.PriorityQueue;

public class GameRank {
  private final int MAX_RANK = 50;



  GameRank(){

  }
  public static class Power implements Comparable<Power>{
    public String name;
    public int power;
    @Override
    public int compareTo(Power power) {
      return this.power - power.power;
    }
  }

}
