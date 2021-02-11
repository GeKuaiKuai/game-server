package protocol;

import game.GameRank;

import java.util.List;

public class SGetRanks extends GameProtocol {
    public List<GameRank.LevelRank> levelRanks;
    public List<GameRank.MoneyRank> moneyRanks;
    public List<GameRank.BattleRank> battleRanks;

    @Override
    public String toString() {
        return "SGetRanks{" +
                "levelRanks=" + levelRanks +
                ", moneyRanks=" + moneyRanks +
                ", battleRanks=" + battleRanks +
                '}';
    }
}
