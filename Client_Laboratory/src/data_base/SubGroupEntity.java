package data_base;

public class SubGroupEntity {
    private int subGroupId;
    private String subGroupName;
    private int groupId;

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
}
