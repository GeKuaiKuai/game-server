package protocol;

public class SSyncBattleComment extends GameProtocol {
    public long time;
    public String content;

    @Override
    public String toString() {
        return "SSyncBattleComment{" +
                "time=" + time +
                ", content='" + content + '\'' +
                '}';
    }
}
