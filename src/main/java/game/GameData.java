package game;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameData {


    private static GameData instance = new GameData();

    public Map<String, PlayerData> playersData = new ConcurrentHashMap<>();

    public Map<String, BattleInfo> battleInfoMap = new ConcurrentHashMap<>();

    public static GameData getInstance(){
        return instance;
    }

    public GameRank rank = new GameRank();

    public PlayerData getPlayerData(String id){
        return playersData.get(id);
    }

    public void addPlayerData(PlayerData data){
        playersData.put(data.id, data);
    }

    public Map<String, PlayerData> getPlayersData() {
        return playersData;
    }

    public boolean isExistID(String id){
        return  playersData.containsKey(id);
    }

    public BattleInfo getBattleInfo(String battleName){
        battleInfoMap.putIfAbsent(battleName, new BattleInfo());
        return battleInfoMap.get(battleName);
    }



}
