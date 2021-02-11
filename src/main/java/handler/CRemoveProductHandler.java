package handler;

import annotations.React;
import game.Service.GameMapRsyncLoop;
import io.OnlineContext;
import manager.ServiceManager;
import protocol.CRemoveProduct;
import protocol.SRemoveProduct;

@React(CRemoveProduct.class)
public class CRemoveProductHandler extends GameHandler<CRemoveProduct> {

    @Override
    public void handle(OnlineContext ctx, CRemoveProduct data) {
        var service = ServiceManager.getInstance().getLoop(GameMapRsyncLoop.class);
        var booth = service.getBooth(ctx.playerData);
        if(booth == null){
            return;
        }
        booth.removeItem(data.productId);
        booth.removePet(data.productId);
        var res = new SRemoveProduct();
        res.type = data.type;
        res.productId = data.productId;
        ctx.channel.write(res);

        booth.broadBoothUpdate(null);
    }
}
