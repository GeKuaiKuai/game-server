package handler;

import annotations.React;
import game.GameData;
import game.PlayerData;
import io.OnlineContext;
import protocol.CCreateSaveID;
import protocol.SCreateSaveID;

@React(CCreateSaveID.class)
public class CCreateSaveHandler extends GameHandler<CCreateSaveID>{

  @Override
  public void handle(OnlineContext ctx, CCreateSaveID data) {
    long ms = System.currentTimeMillis();
    while(GameData.getInstance().isExistID(String.valueOf(ms))){
      ms += 1;
    }
    PlayerData playerData = new PlayerData();
    playerData.id = String.valueOf(ms);
    playerData.name = data.name;
    GameData.getInstance().addPlayerData(playerData);

    var res = new SCreateSaveID();
    res.id = playerData.id;
    ctx.channel.write(res);
  }
}
