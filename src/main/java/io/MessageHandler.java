package io;

import game.PlayerData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import manager.HandlerManager;
import manager.OnlineManager;
import protocol.GameProtocol;
import protocol.JsonMessage;

public class MessageHandler extends ChannelInboundHandlerAdapter {
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
                System.out.println("[info] receive msg "+protocol.toString());
                HandlerManager.getInstance().getHandler(name).handle(onlineContext, protocol);
                ctx.channel().flush();
            }catch (Exception e){
                System.out.println(e.toString());
            }

        }
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        var onlineCtx = OnlineManager.getInstance().getCtx(ctx.channel());
        onlineCtx.channel = null;
        OnlineManager.getInstance().removeContext(ctx.channel());
        if(onlineCtx.playerData != null){
            onlineCtx.playerData.status = PlayerData.STATUS.DISCONNECTED;
        }
        super.channelInactive(ctx);
    }
}
