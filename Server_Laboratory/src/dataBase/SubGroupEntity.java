package dataBase;

public class SubGroupEntity {
    private int subGroupId;
    private String subGroupName;
    private int groupId;

    private final static String nameSubGroupTable = "SubGroupEntity";
    private final static String CREATE_SUB_GROUP_TABLE = "create table if not exists " + nameSubGroupTable +
            "('id' integer primary key autoincrement," +
            "'name' text, 'idGroup' int);";

    public void setSubGroupId(int subGroupId) {
        this.subGroupId = subGroupId;
    }

    public void setSubGroupName(String subGroupName) {
        this.subGroupName = subGroupName;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSubGroupId() {
        return subGroupId;
    }

    public String getSubGroupName() {
        return subGroupName;
    }

    public int getGroupId() {
        return groupId;
    }

    public static String getNameSubGroupTable() {
        return nameSubGroupTable;
    }

    public static String getCreateSubGroupTable() {
        return CREATE_SUB_GROUP_TABLE;
    }
}
