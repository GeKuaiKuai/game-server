package protocol;

public class SBoothBuy extends GameProtocol {
    public String id;
    public int type=0;
    public String productId;
    public int count;

    @Override
    public String toString() {
        return "SBoothBuy{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", productId='" + productId + '\'' +
                ", count=" + count +
                '}';
    }
}
