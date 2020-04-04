package handler;

import annotations.React;
import game.Service.GameMapRsyncLoop;
import game.PlayerData;
import io.OnlineContext;
import manager.ServiceManager;
import protocol.CSyncPosition;

@React(CSyncPosition.class)
public class CSyncPositionHandler extends GameHandler<CSyncPosition>{
    @Override
    public void handle(OnlineContext ctx, CSyncPosition data) {

        PlayerData.Position position = new PlayerData.Position();
        position.status = data.status;
        position.mapId = data.mapId;
        position.direction = data.direction;
        position.x = data.x;
        position.y = data.y;
        position.rad = data.rad;

        if(ctx.playerData.position.isNeedSync(position)){
            ctx.playerData.position = position;
        }else{
            return;
        }

        ServiceManager.getInstance().getLoop(GameMapRsyncLoop.class).syncPositionSequence(ctx.playerData);
    }
}
