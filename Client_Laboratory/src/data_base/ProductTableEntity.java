package data_base;

public class ProductTableEntity {
    private int productId;
    private String productName;
    private String groupName;
    private String subGroupName;
    private double amount;
    private double price;

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setSubGroupName(String subGroupName) {
        this.subGroupName = subGroupName;
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

    public String getGroupName() {
        return groupName;
    }

    public String getSubGroupName() {
        return subGroupName;
    }

    public double getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }
}
