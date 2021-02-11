package game.Service;

import game.GameData;
import game.GameRank;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameRankLoop extends GameFrameLoop{
  private static Logger logger = LogManager.getLogger(GameRankLoop.class);

  private List<GameRank.LevelRank> levelRanks = null;
  private List<GameRank.MoneyRank> moneyRanks = null;
  private List<GameRank.BattleRank> battleRanks = null;

  public List<GameRank.LevelRank> getLevelRanks(){
      if(levelRanks==null){
        return new ArrayList<>();
      }else{
        return levelRanks;
      }
  }

  public List<GameRank.MoneyRank> getMoneyRanks(){
    if(moneyRanks==null){
      return new ArrayList<>();
    }else{
      return moneyRanks;
    }
  }

  public List<GameRank.BattleRank> getBattleRanks(){
    if(battleRanks==null){
      return new ArrayList<>();
    }else{
      return battleRanks;
    }
  }


  @Override
  protected long getPeriod() {
    return 60*1000;
  }

  @Override
  protected void work() {
    levelRanks = GameData.getInstance().rank.getLevelRankList();
    Collections.sort(levelRanks);
    moneyRanks = GameData.getInstance().rank.getMoneyRankList();
    Collections.sort(moneyRanks);
    battleRanks = GameData.getInstance().rank.getBattleRankList();
    Collections.sort(battleRanks);
    logger.info("level ranks sort, num is "+levelRanks.size());
    logger.info("money ranks sort, num is "+moneyRanks.size());
    logger.info("battle ranks sort, num is "+battleRanks.size());
  }

}
