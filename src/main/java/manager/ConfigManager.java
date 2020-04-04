package manager;

import utils.Functions;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    public static ConfigManager instance = new ConfigManager();

    private Map<Class<?>,Object> configMap = new HashMap<>();

    public static ConfigManager getInstance(){
        return instance;
    }

    public ConfigManager()  {
        String packageName = "config";
        var result = Functions.getScanResultByPackageName(packageName);

        for(var tmp:result.getAllClasses()){
            try{
                Class<?> cls = Class.forName(tmp.getName());
                Object object = cls.getDeclaredConstructor().newInstance();
                configMap.put(cls, object);
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }



    @SuppressWarnings("unchecked")
    public <T> T config(Class<T> c){
        return (T)configMap.get(c);
    }



}
