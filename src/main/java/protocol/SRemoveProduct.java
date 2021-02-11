package protocol;

public class SRemoveProduct extends GameProtocol {
    public int type = 0;
    public String productId;

    @Override
    public String toString() {
        return "SRemoveProduct{" +
                "type=" + type +
                ", productId='" + productId + '\'' +
                '}';
    }
}