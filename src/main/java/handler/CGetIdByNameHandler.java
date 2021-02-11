package handler;

import annotations.React;
import game.GameData;
import io.OnlineContext;
import protocol.CGetIdByName;
import protocol.SGetIdByName;

@React(CGetIdByName.class)
public class CGetIdByNameHandler extends GameHandler<CGetIdByName> {
    @Override
    public void handle(OnlineContext ctx, CGetIdByName data) throws InterruptedException {
        var gameData = GameData.getInstance();
        var res = new SGetIdByName();
        gameData.getPlayersData().entrySet().forEach(playerData -> {
            if(playerData.getValue().name.equals(data.name)){
                res.id.add(playerData.getValue().id);
            }
        });
        ctx.channel.write(res);
    }
}
