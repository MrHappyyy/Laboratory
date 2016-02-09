package data_base;

public class ProductEntity {
    private int productId;
    private String productName;
    private int groupId;
    private int subGroupId;
    private double amount;
    private double price;

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setSubGroupId(int subGroupId) {
        this.subGroupId = subGroupId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getSubGroupId() {
        return subGroupId;
    }

    public double getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }
}
