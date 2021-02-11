package protocol;

public class CGetIdByName extends GameProtocol {
    public String name;

    @Override
    public String toString() {
        return "CGetIdByName{" +
                "name='" + name + '\'' +
                '}';
    }
}
