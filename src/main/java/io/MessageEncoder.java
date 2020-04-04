package io;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import protocol.GameProtocol;
import protocol.JsonMessage;

public class MessageEncoder extends MessageToByteEncoder<GameProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, GameProtocol msg, ByteBuf out) throws Exception {
        String name = msg.getClass().getSimpleName();
        JsonMessage jm = GameCode.encodeToJsonMessageFromObject(name, msg);
        String data = GameCode.encodeToStringFromJsonMessage(jm);
        var bytes = data.getBytes();
        System.out.println("[info] send msg "+data + " len:"+bytes.length);
        out.writeInt(bytes.length);
        out.writeBytes(data.getBytes());
    }
}
