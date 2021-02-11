package handler;

import annotations.React;
import game.Service.GameMapRsyncLoop;
import io.OnlineContext;
import manager.ServiceManager;
import protocol.CCreateBooth;
import protocol.NotifySave;
import protocol.SCreateBooth;

@React(CCreateBooth.class)
public class CCreateBoothHandler extends GameHandler<CCreateBooth>{

    @Override
    public void handle(OnlineContext ctx, CCreateBooth data) {
        ServiceManager.getInstance().getLoop(GameMapRsyncLoop.class).createBooth(ctx.playerData);
        var res = new SCreateBooth();
        res.name = "杂货摊位";
        ctx.channel.write(res);
        var saveNotify = new NotifySave();
        ctx.channel.write(saveNotify);
    }
}
