package dataBase;

public class GroupEntity {
    private int groupId;
    private String groupName;

    private final static String nameGroupTable = "GroupEntity";
    private final static String CREATE_GROUP_TABLE = "create table if not exists " + nameGroupTable +
            "('id' integer primary key autoincrement," +
            "'name' text);";

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public static String getNameGroupTable() {
        return nameGroupTable;
    }

    public static String getCreateGroupTable() {
        return CREATE_GROUP_TABLE;
    }
}
