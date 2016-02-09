package dataBase;

public class ProductEntity {
    private int productId;
    private String productName;
    private int groupId;
    private int subGroupId;
    private double amount;
    private double price;

    private final static String nameProductTable = "ProductEntity";
    private final static String CREATE_PRODUCT_TABLE = "create table if not exists " + nameProductTable +
            "('id' integer primary key autoincrement," +
            " 'name' text, 'idSubGroup' int, 'idGroup' int," +
            " 'amount' double, 'price' double);";

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

    public static String getNameProductTable() {
        return nameProductTable;
    }

    public static String getCreateProductTable() {
        return CREATE_PRODUCT_TABLE;
    }
}
