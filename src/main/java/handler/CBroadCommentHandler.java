package handler;

import annotations.React;
import game.Service.GameMapRsyncLoop;
import io.OnlineContext;
import manager.ServiceManager;
import protocol.CBroadComment;

@React(CBroadComment.class)
public class CBroadCommentHandler extends GameHandler<CBroadComment> {

    @Override
    public void handle(OnlineContext ctx, CBroadComment data) {
        GameMapRsyncLoop.Comment comment = new GameMapRsyncLoop.Comment();
        comment.type = GameMapRsyncLoop.COMMENT_TYPE.values()[data.type];
        comment.content = data.content;
        ServiceManager.getInstance().getLoop(GameMapRsyncLoop.class).syncComment(ctx.playerData, comment);
    }
}
