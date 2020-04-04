package io;

import game.PlayerData;
import io.netty.channel.Channel;

public class OnlineContext {
    public Channel channel;
    public PlayerData playerData;

    public boolean isLogin(){
        if(playerData == null){
            return false;
        }
        if(playerData.status != PlayerData.STATUS.NORMAL){
            return false;
        }
        return true;
    }
}
