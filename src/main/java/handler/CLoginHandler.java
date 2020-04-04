package handler;

import annotations.React;
import game.GameData;
import game.Service.GameMapRsyncLoop;
import game.PlayerData;
import io.OnlineContext;
import manager.ServiceManager;
import protocol.CLogin;
import protocol.SLogin;
import utils.ResponseResult;

@React(CLogin.class)
public class CLoginHandler extends GameHandler<CLogin> {
    @Override
    public void handle(OnlineContext ctx, CLogin data) {
        var playerData = GameData.getInstance().getPlayerData(data.id);
        var res = new SLogin();
        if(playerData == null){
            res.result = ResponseResult.SAVE_ID_ERROR.toString();
            return;
        }
        ctx.playerData = playerData;
        playerData.channel = ctx.channel;
        playerData.status = PlayerData.STATUS.NORMAL;
        ServiceManager.getInstance().getLoop(GameMapRsyncLoop.class).addNewOnlinePlayer(playerData);

        res.result = ResponseResult.SUCCESS.toString();
        ctx.channel.write(res);

        // 处理缓存的协议
        sendProtocolQueue(ctx);
    }

    public void sendProtocolQueue(OnlineContext ctx){
        var playerData = ctx.playerData;
        var queue = playerData.protocolsQueue;
        while(queue.size() > 0){
            var protocol = queue.remove(0);

        }
    }
}
