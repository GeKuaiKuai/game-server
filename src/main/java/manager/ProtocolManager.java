package manager;

import io.github.classgraph.ScanResult;
import protocol.GameProtocol;
import utils.Functions;

import java.util.HashMap;
import java.util.Map;

public class ProtocolManager {
    public static ProtocolManager instance = new ProtocolManager();
    private Map<String, Class<GameProtocol>> protocolMap = new HashMap<>();

    public static ProtocolManager getInstance(){
        return instance;
    }

    @SuppressWarnings("unchecked")
    public ProtocolManager(){
        ScanResult result = Functions.getScanResultByPackageName("protocol");
        for(var tmp:result.getAllClasses()){
            try{
                Class<GameProtocol> cls = (Class<GameProtocol>)Class.forName(tmp.getName());
                protocolMap.put(tmp.getSimpleName(), cls);
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }

    public Class<GameProtocol> getProtocolClass(String name){
        return protocolMap.get(name);
    }

}
