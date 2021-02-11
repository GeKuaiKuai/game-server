package handler;

import annotations.React;
import game.Service.GameMapRsyncLoop;
import io.OnlineContext;
import manager.ServiceManager;
import protocol.CCloseBooth;
import protocol.SCloseBooth;

@React(CCloseBooth.class)
public class CCloseBoothHandler extends GameHandler<CCloseBooth> {
    @Override
    public void handle(OnlineContext ctx, CCloseBooth data) {
        var mapSyncService = ServiceManager.getInstance().getLoop(GameMapRsyncLoop.class);
        var booth = mapSyncService.getBooth(ctx.playerData);
        if(booth == null){
            return;
        }
        mapSyncService.closeBooth(ctx.playerData);
        var res = new SCloseBooth();
        ctx.channel.write(res);
    }
}
