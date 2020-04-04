package protocol;

public class CBroadComment extends GameProtocol {
    public int type;
    public String content;

    @Override
    public String toString() {
        return "CBroadComment{" +
                "type=" + type +
                ", content='" + content + '\'' +
                '}';
    }
}
