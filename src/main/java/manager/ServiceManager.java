package manager;

import game.Service.GameFrameLoop;

import java.util.HashMap;
import java.util.Map;

public class ServiceManager {
    private static ServiceManager instance = new ServiceManager();

    public Map<Class<?>, GameFrameLoop> loopMap = new HashMap<>();

    public static ServiceManager getInstance(){
        return instance;
    }

    public void addLoop(GameFrameLoop loop){
        loopMap.put(loop.getClass(), loop);
    }

    public <T>T getLoop(Class<T> cls){
        return (T)loopMap.get(cls);
    }

    public void start(){
        loopMap.forEach((i,v) -> {
            v.start();
        });
    }
}
