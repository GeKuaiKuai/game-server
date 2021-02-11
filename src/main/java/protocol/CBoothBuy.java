package protocol;

public class CBoothBuy extends GameProtocol {
    public String id;
    public int type=0;
    public String productId;
    public int count;

    @Override
    public String toString() {
        return "CBoothBuy{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", productId='" + productId + '\'' +
                ", count=" + count +
                '}';
    }
}
