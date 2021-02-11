package game.Service;

import com.alibaba.fastjson.JSON;
import game.BattleInfo;
import game.GameData;
import game.GameRank;
import game.PlayerData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameStoreLoop extends GameFrameLoop {
    private static Logger logger = LogManager.getLogger(GameStoreLoop.class);
    protected long EXE_PERIOD = 10 * 1000;

    public static class DataStore{
        public List<PlayerData.PlayerDataStore> players = new ArrayList<>();
        public Map<String, BattleInfo> battleInfoMap = new ConcurrentHashMap<>();
        public GameRank rank = new GameRank();
    }

    @Override
    protected long getPeriod() {
        return EXE_PERIOD;
    }

    @Override
    protected void work() {
        save();
    }

    private void save() {
        String path = "./playerData.txt";
        var file = Paths.get(path).toFile();
        if (!file.exists()) {
            boolean res = false;
            try {
                res = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!res){
                return;
            }
        }

        DataStore dataStore = new DataStore();
        var gameDatas = GameData.getInstance().getPlayersData();
        gameDatas.forEach((key, value) -> {
            dataStore.players.add(value.getDataStore());
        });

        dataStore.battleInfoMap = GameData.getInstance().battleInfoMap;
        dataStore.rank = GameData.getInstance().rank;

        try (FileOutputStream out = new FileOutputStream(file);
             BufferedOutputStream buffer = new BufferedOutputStream(out)) {
            buffer.write(JSON.toJSONString(dataStore).getBytes());
            buffer.flush();
            out.getFD().sync();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void load() {
        String path = "./playerData.txt";
        var file = Paths.get(path).toFile();
        if (!file.exists()) {
            logger.error("file is not exist");
            return;
        }
        try (FileInputStream in = new FileInputStream(file);) {

            byte[] bytes = new byte[(int)file.length()];
            in.read(bytes);
            in.close();
            var dataStore = JSON.parseObject(new String(bytes), DataStore.class);
            if(dataStore == null){
                logger.error("dataStore is null");
                return;
            }
            GameData gameData = GameData.getInstance();
            dataStore.players.forEach(data -> {
                PlayerData playerData = new PlayerData(data);
                gameData.addPlayerData(playerData);
            });
            GameData.getInstance().battleInfoMap = dataStore.battleInfoMap;
            GameData.getInstance().rank = dataStore.rank;
            logger.info("user num is "+dataStore.players.size());
            logger.info("battle comment num is "+dataStore.battleInfoMap.size());

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
