package protocol;

public class CRemoveProduct extends GameProtocol {
    public int type = 0;
    public String productId;

    @Override
    public String toString() {
        return "CRemoveProduct{" +
                "type=" + type +
                ", productId='" + productId + '\'' +
                '}';
    }
}
