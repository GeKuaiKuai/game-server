package io;

import game.PlayerData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import manager.HandlerManager;
import manager.OnlineManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import protocol.GameProtocol;
import protocol.JsonMessage;

public class MessageHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LogManager.getLogger(MessageHandler.class);
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        var onlineCtx = OnlineManager.getInstance().createContext(ctx.channel());
        onlineCtx.channel = ctx.channel();
        super.channelActive(ctx);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof JsonMessage){
            try{
                var jm = (JsonMessage)msg;
                String name = jm.name;
                GameProtocol protocol = GameCode.decodeToGameProtocolFromJsonMessage(jm);
                var onlineContext = OnlineManager.getInstance().getCtx(ctx.channel());
                // 拦截非登录情况的违法请求(这里有必要吗？
                if(!HandlerManager.getInstance().validate(onlineContext, name)){
                    return;
                }
                if(onlineContext.playerData != null){
                    logger.info(onlineContext.playerData.id + " receive msg "+protocol.toString());
                }else{
                    logger.info("not login receive msg "+protocol.toString());
                }
                HandlerManager.getInstance().getHandler(name).handle(onlineContext, protocol);
                ctx.channel().flush();
            }catch (Exception e){
                logger.error("",e);
            }

        }
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        var onlineCtx = OnlineManager.getInstance().getCtx(ctx.channel());
        if(onlineCtx.channel != ctx.channel()){
            return;
        }
        onlineCtx.channel = null;
        OnlineManager.getInstance().removeContext(ctx.channel());
        if(onlineCtx.playerData != null){
            logger.info(onlineCtx.playerData.id+" disconnect game server");
            onlineCtx.playerData.status = PlayerData.STATUS.DISCONNECTED;
            onlineCtx.playerData.channel = null;
        }
        super.channelInactive(ctx);
    }
}
