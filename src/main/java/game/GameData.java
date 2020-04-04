package game;

import java.util.concurrent.ConcurrentHashMap;
import protocol.GameProtocol;
import protocol.NotifyPlayerPosition;
import protocol.NotifyRoleInfo;

import java.util.*;

public class GameData {


    private static GameData instance = new GameData();

    private Map<String, PlayerData> playersData = new ConcurrentHashMap<>();

    private Map<String, UniqueData> petData = new ConcurrentHashMap<>();

    private Map<String, UniqueData> weaponData = new ConcurrentHashMap<>();

    private Map<String, UniqueData> equipData = new ConcurrentHashMap<>();

    public static GameData getInstance(){
        return instance;
    }

    public PlayerData getPlayerData(String id){
        return playersData.get(id);
    }

    public void addPlayerData(PlayerData data){
        playersData.put(data.id, data);
    }

    public boolean isExistID(String id){
        return  playersData.containsKey(id);
    }



}
