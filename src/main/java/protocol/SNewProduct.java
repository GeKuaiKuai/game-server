package protocol;

public class SNewProduct extends GameProtocol {
    public int type=0;
    public String productId;
    public int index;
    public int price;

    @Override
    public String toString() {
        return "SNewProduct{" +
                "type=" + type +
                ", productId='" + productId + '\'' +
                ", index=" + index +
                ", price=" + price +
                '}';
    }
}
