package handler;

import annotations.React;
import game.GameData;
import game.Service.GameMapRsyncLoop;
import io.OnlineContext;
import manager.ServiceManager;
import protocol.CBoothBuy;
import protocol.NotifyBoothBuy;
import protocol.NotifySave;
import protocol.SBoothBuy;

@React(CBoothBuy.class)
public class CBoothBuyHandler extends GameHandler<CBoothBuy> {

    @Override
    public void handle(OnlineContext ctx, CBoothBuy data) {
        var service = ServiceManager.getInstance().getLoop(GameMapRsyncLoop.class);
        var gameData = GameData.getInstance().getPlayerData(data.id);
        var booth = service.getBooth(gameData);
        if(booth == null){
            return;
        }
        boolean result = false;
        result = booth.buyItem(data.productId, data.count);

        if(result){
            var saveNotify = new NotifySave();
            // 通知摊主
            var notify = new NotifyBoothBuy();
            notify.name = ctx.playerData.name;
            notify.productId = data.productId;
            notify.count = data.count;
            gameData.channel.write(notify);
            gameData.channel.write(saveNotify);


            // 查看者
            booth.broadBoothUpdate(ctx.playerData.id);

            // 顾客
            var res = new SBoothBuy();
            res.id = data.id;
            res.productId = data.productId;
            res.type = data.type;
            res.count = data.count;
            ctx.channel.write(res);
            ctx.channel.write(saveNotify);
        }
    }
}
