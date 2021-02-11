package handler;

import annotations.React;
import game.Service.GameMapRsyncLoop;
import io.OnlineContext;
import manager.ServiceManager;
import protocol.CSetBoothName;
import protocol.SSetBoothName;

@React(CSetBoothName.class)
public class CSetBoothNameHandler extends GameHandler<CSetBoothName>{

    @Override
    public void handle(OnlineContext ctx, CSetBoothName data) {
        var mapSyncService = ServiceManager.getInstance().getLoop(GameMapRsyncLoop.class);
        var booth = mapSyncService.getBooth(ctx.playerData);
        booth.name = data.name;
        mapSyncService.syncDataSequence(ctx.playerData);
        var res = new SSetBoothName();
        res.name = booth.name;
        ctx.channel.write(res);
    }
}
