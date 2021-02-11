package manager;

import annotations.React;
import game.PlayerData;
import handler.GameHandler;
import io.OnlineContext;
import io.github.classgraph.ScanResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandlerManager {
    private static Logger logger = LogManager.getLogger(HandlerManager.class);
    private static HandlerManager instance = new HandlerManager();
    private Map<String, GameHandler> handlerClassMap = new HashMap<>();
    private List<String> loginWhiteList = new ArrayList<>();

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
            logger.error("",e);
        }

        loginWhiteList.add("CLogin");
        loginWhiteList.add("CCreateSaveID");

    }

    public static HandlerManager getInstance(){
        return instance;
    }

    @SuppressWarnings("unchecked")
    public GameHandler getHandler(String name){
        return handlerClassMap.get(name);
    }

    public boolean validate(OnlineContext ctx, String name){
        if(!handlerClassMap.containsKey(name)){
            logger.error("can not find protocol "+name);
            return false;
        }
        if(ctx.playerData == null || ctx.playerData.status != PlayerData.STATUS.NORMAL){
            if(!loginWhiteList.contains(name)){
                return false;
            }
        }
        if(ctx.playerData != null){
            if(ctx.playerData.channel != ctx.channel){
                logger.error(ctx.playerData.id+" bad bind channel");
                return false;
            }
        }
        return true;
    }

}
