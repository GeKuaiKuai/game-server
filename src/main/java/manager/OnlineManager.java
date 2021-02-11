package manager;

import io.OnlineContext;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class OnlineManager {
    private static OnlineManager instance = new OnlineManager();

    private static Map<Channel, OnlineContext> ctxMap = new HashMap<>();
    private static final Object ctxMapLock = new Object();

    public static OnlineManager getInstance(){
        return instance;
    }

    public OnlineContext getCtx(Channel channel){
        return ctxMap.get(channel);
    }

    public OnlineContext createContext(Channel channel){
        var ctx = new OnlineContext();
        synchronized(ctxMapLock) {
            ctxMap.put(channel, ctx);
        }
        return ctx;
    }

    public void removeContext(Channel channel){
        synchronized(ctxMapLock){
            ctxMap.remove(channel);
        }

    }

}
