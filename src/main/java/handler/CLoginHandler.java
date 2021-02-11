package handler;

import annotations.React;
import game.GameData;
import game.PlayerData;
import game.Service.GameMapRsyncLoop;
import io.OnlineContext;
import manager.ServiceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import protocol.CLogin;
import protocol.NotifyScript;
import protocol.SLogin;
import utils.ResponseResult;

@React(CLogin.class)
public class CLoginHandler extends GameHandler<CLogin> {
    private static Logger logger = LogManager.getLogger(CLoginHandler.class);
    private static int version = 7;
    @Override
    public void handle(OnlineContext ctx, CLogin data) throws InterruptedException {

        var playerData = GameData.getInstance().getPlayerData(data.id);
        var res = new SLogin();
        res.version = version;

        if(playerData == null){
            res.result = ResponseResult.SAVE_ID_ERROR.toString();
            ctx.channel.write(res);
            return;
        }

        // 踢人
        if(playerData.channel != null && playerData.isOnline()){
            ServiceManager.getInstance().getLoop(GameMapRsyncLoop.class).removeOnlinePlayer(playerData);
            var kick = new NotifyKick();
            playerData.channel.writeAndFlush(kick);
        }

        ctx.playerData = playerData;
        playerData.channel = ctx.channel;
        playerData.status = PlayerData.STATUS.NORMAL;
        ServiceManager.getInstance().getLoop(GameMapRsyncLoop.class).addNewOnlinePlayer(playerData);

        res.result = ResponseResult.SUCCESS.toString();
        res.lastSaveTime = playerData.lastSaveTime;
        res.over = playerData.over;
        ctx.channel.write(res);

        if(data.version == version){
            // 处理缓存的协议
            sendProtocolQueue(ctx);
        }

    }

    public void sendProtocolQueue(OnlineContext ctx){
        var playerData = ctx.playerData;
        var queue = playerData.luaQueue;
        while(queue.size() > 0){
            var script = queue.remove(0);
            var notify = new NotifyScript();
            notify.script = script;
            ctx.channel.write(notify);
        }
    }
}
