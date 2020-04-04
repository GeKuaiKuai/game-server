package manager;

import annotations.React;
import handler.GameHandler;
import io.github.classgraph.ScanResult;
import utils.Functions;

import java.util.HashMap;
import java.util.Map;

public class HandlerManager {
    private static HandlerManager instance = new HandlerManager();
    private Map<String, GameHandler> handlerClassMap = new HashMap<>();
    public HandlerManager(){
        try{
            ScanResult result = Functions.getScanResultByPackageName("handler");
            for(var tmp:result.getAllClasses()){
                if(tmp.hasAnnotation(React.class.getName())){
                    Class<?> cls = Class.forName(tmp.getName());
                    var value = cls.getAnnotation(React.class).value();
                    GameHandler handler = (GameHandler)Class.forName(tmp.getName()).getConstructor().newInstance();
                    handlerClassMap.put(value.getSimpleName(), handler);
                }
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }

    }

    public static HandlerManager getInstance(){
        return instance;
    }

    @SuppressWarnings("unchecked")
    public GameHandler getHandler(String name){
        return handlerClassMap.get(name);
    }

}
