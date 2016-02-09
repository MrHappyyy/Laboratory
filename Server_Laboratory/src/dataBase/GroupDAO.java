package dataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO implements DAO<GroupEntity> {
    private Connection connection;

    public GroupDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTableDataBase() {
        try {
            PreparedStatement product = connection.prepareStatement(GroupEntity.getCreateGroupTable());
            int result = product.executeUpdate();
            product.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<GroupEntity> getAll() {
        List<GroupEntity> groupList = new ArrayList<GroupEntity>();

        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM " + GroupEntity.getNameGroupTable());

            while (res.next()) {
                GroupEntity groupEntity = new GroupEntity();
                groupEntity.setGroupId(res.getInt("id"));
                groupEntity.setGroupName(res.getString("name"));

                groupList.add(groupEntity);
            }
            statement.close();
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupList;
    }

    @Override
    public List<GroupEntity> getBuyName(String groupName) {
        List<GroupEntity> groupList = new ArrayList<GroupEntity>();

        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM " + GroupEntity.getNameGroupTable() + " WHERE name = '" + groupName + "'");

            while (res.next()) {
                GroupEntity groupEntity = new GroupEntity();
                groupEntity.setGroupId(res.getInt("id"));
                groupEntity.setGroupName(res.getString("name"));

                groupList.add(groupEntity);
            }
            statement.close();
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupList;
    }

    @Override
    public boolean add(GroupEntity entity) {
        boolean isInsert = false;

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " + GroupEntity.getNameGroupTable() + " (name) VALUES (?)");

            statement.setString(1, entity.getGroupName());

            int result = statement.executeUpdate();
            isInsert = true;
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isInsert;
    }

    @Override
    public boolean update(int id, GroupEntity entity) {
        boolean isUpdate = false;

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE " + GroupEntity.getNameGroupTable() +
                    " SET name = '" + entity.getGroupName() + "' WHERE id = '" + id + "'");

            int result = statement.executeUpdate();
            isUpdate = true;
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdate;
    }

    @Override
    public boolean delete(int id) {
        boolean isDelete = false;

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM " + GroupEntity.getNameGroupTable() + " WHERE id = '" + id + "'");
            int result = statement.executeUpdate();
            isDelete = true;
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDelete;
    }
}
