package handler;

import annotations.React;
import io.OnlineContext;
import protocol.CTimeTick;

@React(CTimeTick.class)
public class CTimeTickHandler extends GameHandler<CTimeTick> {
    @Override
    public void handle(OnlineContext ctx, CTimeTick data) throws InterruptedException {

    }
}
