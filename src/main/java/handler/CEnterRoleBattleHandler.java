package handler;

import annotations.React;
import game.GameData;
import io.OnlineContext;
import protocol.CEnterRoleBattle;
import protocol.SEnterRoleBattle;
@React(CEnterRoleBattle.class)
public class CEnterRoleBattleHandler extends GameHandler<CEnterRoleBattle> {
    @Override
    public void handle(OnlineContext ctx, CEnterRoleBattle data) {
        SEnterRoleBattle res = new SEnterRoleBattle();
        res.comments = GameData.getInstance().getBattleInfo(data.name).commentList;
        ctx.channel.write(res);
    }
}
