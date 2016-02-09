package dataBase;

/**
 * Created by mrhappyyy on 08.02.16.
 */
public class StatisticsEntity {
    private int id;
    private String name;
    private String group;
    private String subGroup;
    private double price;
    private double amountSell;
    private double priceSell;
    private String data;

    private final static String nameStatisticsTable = "StatisticsEntity";
    private final static String CREATE_STATISTICS_TABLE = "create table if not exists " + nameStatisticsTable +
            "('id' integer primary key autoincrement," +
            " 'name' text, 'groupName' text, 'subGroupName' text," +
            " 'price' double, 'amountSell' double, 'priceSell' double, 'data' text);";


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public String getSubGroup() {
        return subGroup;
    }

    public double getPrice() {
        return price;
    }

    public double getAmountSell() {
        return amountSell;
    }

    public double getPriceSell() {
        return priceSell;
    }

    public String getData() {
        return data;
    }

    public static String getNameStatisticsTable() {
        return nameStatisticsTable;
    }

    public static String getCreateStatisticsTable() {
        return CREATE_STATISTICS_TABLE;
    }

    public void setId(int id) {

        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setSubGroup(String subGroup) {
        this.subGroup = subGroup;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAmountSell(double amountSell) {
        this.amountSell = amountSell;
    }

    public void setPriceSell(double priceSell) {
        this.priceSell = priceSell;
    }

    public void setData(String data) {
        this.data = data;
    }
}
