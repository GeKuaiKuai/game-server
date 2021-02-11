package handler;

import annotations.React;
import game.GameData;
import game.Service.GameMapRsyncLoop;
import io.OnlineContext;
import manager.ServiceManager;
import protocol.COpenBoothWindow;
import protocol.SOpenBoothWindow;

@React(COpenBoothWindow.class)
public class COpenBoothWindowHandler extends GameHandler<COpenBoothWindow> {

    @Override
    public void handle(OnlineContext ctx, COpenBoothWindow data) {
        var service = ServiceManager.getInstance().getLoop(GameMapRsyncLoop.class);
        var gameData = GameData.getInstance().getPlayerData(data.id);
        var booth = service.getBooth(gameData);
        if(booth == null){
            return;
        }
        booth.addVisitors(ctx.playerData);
        var res = new SOpenBoothWindow();
        res.id = data.id;
        res.name = booth.name;
        res.items = booth.getItems();
        res.pets = booth.getPets();
        res.productData = booth.getProductData();
        ctx.channel.write(res);
    }
}
