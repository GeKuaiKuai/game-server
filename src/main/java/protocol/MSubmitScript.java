package protocol;

public class MSubmitScript extends GameProtocol{
    public String script;
    public String id;

    @Override
    public String toString() {
        return "MSubmitScript{" +
                "script='" + script + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
