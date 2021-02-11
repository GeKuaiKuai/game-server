package handler;

import annotations.React;
import game.Service.GameRankLoop;
import io.OnlineContext;
import manager.ServiceManager;
import protocol.CGetRanks;
import protocol.SGetRanks;

@React(CGetRanks.class)
public class CGetRanksHandler extends GameHandler<CGetRanks> {

    @Override
    public void handle(OnlineContext ctx, CGetRanks data) throws InterruptedException {
        var res = new SGetRanks();
        var rankService = ServiceManager.getInstance().getLoop(GameRankLoop.class);
        res.levelRanks = rankService.getLevelRanks();
        res.moneyRanks = rankService.getMoneyRanks();
        res.battleRanks = rankService.getBattleRanks();
        ctx.channel.write(res);
    }
}
