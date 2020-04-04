package io;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import protocol.JsonMessage;

import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while(in.readableBytes() > GameCode.getHeadLength()){
            in.markReaderIndex();
            int len = GameCode.getMessageHead(in);

            if(len <= 0){
                ctx.close();
            }

            if(in.readableBytes() < len){
                in.resetReaderIndex();
                return;
            }
            byte[] buf = new byte[len];
            in.readBytes(buf);
            JsonMessage jm = GameCode.decodeToJsonMessageFromString(new String(buf));
            out.add(jm);
        }
    }
}
