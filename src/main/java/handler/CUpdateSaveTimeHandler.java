package handler;

import annotations.React;
import game.GameData;
import game.GameRank;
import io.OnlineContext;
import protocol.CUpdateSaveTime;

@React(CUpdateSaveTime.class)
public class CUpdateSaveTimeHandler extends GameHandler<CUpdateSaveTime> {

    @Override
    public void handle(OnlineContext ctx, CUpdateSaveTime data) {
        ctx.playerData.lastSaveTime = data.time;

        if(data.levelRank != null){
            GameRank.LevelRank levelRank = new  GameRank.LevelRank();
            levelRank.name = ctx.playerData.name;
            levelRank.level = data.levelRank.level;
            levelRank.count = data.levelRank.count;
            GameData.getInstance().rank.putLevelRank(ctx.playerData.id, levelRank);
        }

        if(data.moneyRank != null){
            GameRank.MoneyRank moneyRank = new GameRank.MoneyRank();
            moneyRank.name = ctx.playerData.name;
            moneyRank.money = data.moneyRank.money;
            GameData.getInstance().rank.putMoneyRank(ctx.playerData.id, moneyRank);
        }

        if(data.battleRank != null){
            GameRank.BattleRank battleRank = new GameRank.BattleRank();
            battleRank.name = ctx.playerData.name;
            battleRank.battle = data.battleRank.battle;
            GameData.getInstance().rank.putBattleRank(ctx.playerData.id, battleRank);
        }

    }
}
