package protocol;

public class NotifyBoothBuy extends GameProtocol {
    public String name;
    public String productId;
    public int count;

    @Override
    public String toString() {
        return "NotifyBoothBuy{" +
                "name='" + name + '\'' +
                ", productId='" + productId + '\'' +
                ", count=" + count +
                '}';
    }
}
