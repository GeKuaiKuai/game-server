package protocol;

public class SCreateBooth extends GameProtocol {
    public String name;

    @Override
    public String toString() {
        return "SCreateBooth{" +
                "name='" + name + '\'' +
                '}';
    }
}
