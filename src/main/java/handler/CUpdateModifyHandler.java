package handler;

import annotations.React;
import io.OnlineContext;
import protocol.CUpdateModify;
@React(CUpdateModify.class)
public class CUpdateModifyHandler extends GameHandler<CUpdateModify> {

    @Override
    public void handle(OnlineContext ctx, CUpdateModify data) throws InterruptedException {
        ctx.playerData.isModify = true;
    }
}
