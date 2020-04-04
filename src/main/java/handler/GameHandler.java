package handler;

import io.OnlineContext;
import protocol.GameProtocol;

public abstract class GameHandler<T extends GameProtocol> {
    public abstract void handle(OnlineContext ctx, T data);
}
