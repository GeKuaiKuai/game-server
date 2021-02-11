package handler;

import annotations.React;
import game.Service.GameMapRsyncLoop;
import io.OnlineContext;
import manager.ServiceManager;
import protocol.CNewProduct;
import protocol.SNewProduct;

@React(CNewProduct.class)
public class CNewProductHandler extends GameHandler<CNewProduct> {
    @Override
    public void handle(OnlineContext ctx, CNewProduct data) {
        var service = ServiceManager.getInstance().getLoop(GameMapRsyncLoop.class);
        var booth = service.getBooth(ctx.playerData);
        if(booth == null){
            return;
        }
        String productId=null;
        if(data.type == 0){
            productId = booth.addItem(data.price, data.count,data.itemData,data.subData);
        }else if(data.type == 1){
            productId = booth.addPet(data.price, data.subData);
        }

        var res = new SNewProduct();
        res.productId = productId;
        res.index = data.index;
        res.price = data.price;
        res.type = data.type;
        ctx.channel.write(res);

        booth.broadBoothUpdate(null);
    }
}
