package protocol;

import game.BattleInfo;

import java.util.List;

public class SEnterRoleBattle extends GameProtocol {
    public List<BattleInfo.BattleComment> comments;

    @Override
    public String toString() {
        return "SEnterRoleBattle{" +
                "comments=" + comments +
                '}';
    }
}
