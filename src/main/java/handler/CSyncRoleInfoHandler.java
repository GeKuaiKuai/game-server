package handler;

import annotations.React;
import game.Service.GameMapRsyncLoop;
import game.PlayerData.RoleData;
import io.OnlineContext;
import manager.ServiceManager;
import protocol.CSyncRoleInfo;

@React(CSyncRoleInfo.class)
public class CSyncRoleInfoHandler extends GameHandler<CSyncRoleInfo> {
    @Override
    public void handle(OnlineContext ctx, CSyncRoleInfo data) {
        RoleData roleData = new RoleData();
        roleData.dataId = data.data_id;
        roleData.weaponId = data.weapon_id;
        roleData.color = data.color;
        if(ctx.playerData.roleData.isNeedSync(roleData)){
            ctx.playerData.roleData = roleData;
        }else{
            return;
        }
        ServiceManager.getInstance().getLoop(GameMapRsyncLoop.class).syncDataSequence(ctx.playerData);
    }
}
