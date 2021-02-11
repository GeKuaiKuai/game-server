package handler;

import annotations.React;
import game.GameData;
import game.Service.GameMapRsyncLoop;
import io.OnlineContext;
import manager.ServiceManager;
import protocol.CCloseBoothWindow;
import protocol.SCloseBoothWindow;

@React(CCloseBoothWindow.class)
public class CCloseBoothWindowHandler extends GameHandler<CCloseBoothWindow> {

    @Override
    public void handle(OnlineContext ctx, CCloseBoothWindow data) {
        var service = ServiceManager.getInstance().getLoop(GameMapRsyncLoop.class);
        var gameData = GameData.getInstance().getPlayerData(data.id);
        var booth = service.getBooth(gameData);
        if(booth == null){
            return;
        }
        booth.removeVisitors(ctx.playerData);
        var res = new SCloseBoothWindow();
        res.id = data.id;
        ctx.channel.write(res);
    }
}
