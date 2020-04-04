package io;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import manager.ProtocolManager;
import protocol.GameProtocol;
import protocol.JsonMessage;

public class GameCode {
    public static int getHeadLength(){
        return Integer.BYTES;
    }

    public static int getMessageHead(ByteBuf buf){
        return buf.readInt();
    }

    public static JsonMessage decodeToJsonMessageFromString(String data){
        return JSON.parseObject(data, JsonMessage.class);
    }

    public static String encodeToStringFromJsonMessage(JsonMessage object){
        return JSON.toJSONString(object);
    }

    public static GameProtocol decodeToGameProtocolFromJsonMessage(JsonMessage message){
        var name = message.name;
        var data = message.data;
        var cls = ProtocolManager.getInstance().getProtocolClass(name);
        if(cls != null){
            return JSON.parseObject(data, cls);
        }
        return null;
    }

    public static JsonMessage encodeToJsonMessageFromObject(String name, GameProtocol obj){
        JsonMessage message = new JsonMessage();
        message.name = name;
        message.data = JSON.toJSONString(obj);
        return message;
    }

    public static void writeToByteBufFromJsonMessage(JsonMessage jsonMessage, ByteBuf buf){
        String data = JSON.toJSONString(jsonMessage);
        int len = data.length();
        buf.writeInt(len);
        buf.writeBytes(data.getBytes());
    }
}
