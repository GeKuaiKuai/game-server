package handler;

import annotations.React;
import game.BattleInfo;
import game.GameData;
import io.OnlineContext;
import protocol.CSyncBattleComment;
import protocol.SSyncBattleComment;

@React(CSyncBattleComment.class)
public class CSyncBattleCommentHandler extends GameHandler<CSyncBattleComment> {
    @Override
    public void handle(OnlineContext ctx, CSyncBattleComment data) {
        BattleInfo.BattleComment comment = new BattleInfo.BattleComment();
        comment.time = data.time;
        comment.content = data.content;
        GameData.getInstance().getBattleInfo(data.battleName).insertComment(comment);
        var res = new SSyncBattleComment();
        res.time = data.time;
        res.content = data.content;
        ctx.channel.write(res);
    }
}
