package handler;

import annotations.React;
import io.OnlineContext;
import protocol.CReportError;
@React(CReportError.class)
public class CReportErrorHandler extends GameHandler<CReportError> {

    @Override
    public void handle(OnlineContext ctx, CReportError data) throws InterruptedException {

    }
}
