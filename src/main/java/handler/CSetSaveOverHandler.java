package handler;

import annotations.React;
import game.GameData;
import io.OnlineContext;
import protocol.CSetSaveOver;
@React(CSetSaveOver.class)
public class CSetSaveOverHandler extends GameHandler<CSetSaveOver> {

    @Override
    public void handle(OnlineContext ctx, CSetSaveOver data) throws InterruptedException {
        var playerData = GameData.getInstance().getPlayerData(data.id);
        playerData.over = true;
        GameData.getInstance().rank.clearRank(playerData.id);
    }
}
