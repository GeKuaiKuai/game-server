package protocol;

public class CNewProduct extends GameProtocol {
    public int type=0;
    public int index;
    public int count;
    public int price;
    public String itemData;
    public String subData;

    @Override
    public String toString() {
        return "CNewProduct{" +
                "type=" + type +
                ", index=" + index +
                ", count=" + count +
                ", price=" + price +
                ", itemData='" + itemData + '\'' +
                ", subData='" + subData + '\'' +
                '}';
    }
}
