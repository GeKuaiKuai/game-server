package handler;


import annotations.React;
import game.GameData;
import io.OnlineContext;
import protocol.MSubmitScript;
import protocol.NotifyScript;

@React(MSubmitScript.class)
public class MSubmitScriptHandler extends GameHandler<MSubmitScript>{
    @Override
    public void handle(OnlineContext ctx, MSubmitScript data) throws InterruptedException {
        if(data.script == null){
            return;
        }
        var notify = new NotifyScript();
        notify.script = data.script;
        if(data.id == null){
            GameData.getInstance().playersData.forEach((k,v)->{
                if(v.channel!=null){
                    v.channel.writeAndFlush(notify);
                }else{
                    v.luaQueue.add(data.script);
                }
            });
        }else{
            var playerData =  GameData.getInstance().getPlayerData(data.id);
            if(playerData!=null){
                if(playerData.channel != null){
                    playerData.channel.writeAndFlush(notify);
                }else {
                    playerData.luaQueue.add(data.script);
                }
            }
        }
    }
}
