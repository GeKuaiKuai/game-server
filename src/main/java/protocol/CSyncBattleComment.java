package protocol;

public class CSyncBattleComment extends GameProtocol {
    public String battleName;
    public long time;
    public String content;

    @Override
    public String toString() {
        return "CSyncBattleComment{" +
                "battleName='" + battleName + '\'' +
                ", time=" + time +
                ", content='" + content + '\'' +
                '}';
    }
}
