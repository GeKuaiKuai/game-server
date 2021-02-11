package protocol;

import game.GameRank;

public class CUpdateSaveTime extends GameProtocol {
    public int time;
    public GameRank.LevelRank levelRank;
    public GameRank.MoneyRank moneyRank;
    public GameRank.BattleRank battleRank;

    @Override
    public String toString() {
        return "CUpdateSaveTime{" +
                "time=" + time +
                ", levelRank=" + levelRank +
                ", moneyRank=" + moneyRank +
                ", battleRank=" + battleRank +
                '}';
    }
}
