package io;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import manager.OnlineManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import protocol.GameProtocol;
import protocol.JsonMessage;

public class MessageEncoder extends MessageToByteEncoder<GameProtocol> {
    private static Logger logger = LogManager.getLogger(MessageEncoder.class);
    @Override
    protected void encode(ChannelHandlerContext ctx, GameProtocol msg, ByteBuf out) throws Exception {
        String name = msg.getClass().getSimpleName();
        JsonMessage jm = GameCode.encodeToJsonMessageFromObject(name, msg);
        String data = GameCode.encodeToStringFromJsonMessage(jm);
        var bytes = data.getBytes();
        int len = bytes.length;

        var onlineContext = OnlineManager.getInstance().getCtx(ctx.channel());
        var playerData = onlineContext.playerData;
        if(playerData != null){
            logger.info(playerData.id+" send msg " + data + " len:"+len );
        }else{
            logger.info("send msg " + data + " len:"+len );
        }


        out.writeInt(len);
        out.writeBytes(data.getBytes());
    }
}
