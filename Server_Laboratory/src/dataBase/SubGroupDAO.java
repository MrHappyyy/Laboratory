package dataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubGroupDAO implements DAO<SubGroupEntity> {
    private Connection connection;

    public SubGroupDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTableDataBase() {
        try {
            PreparedStatement product = connection.prepareStatement(SubGroupEntity.getCreateSubGroupTable());
            int result = product.executeUpdate();
            product.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SubGroupEntity> getAll() {
        List<SubGroupEntity> subGroupList = new ArrayList<SubGroupEntity>();

        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM " + SubGroupEntity.getNameSubGroupTable());

            while (res.next()) {
                SubGroupEntity subGroup = new SubGroupEntity();
                subGroup.setSubGroupId(res.getInt("id"));
                subGroup.setSubGroupName(res.getString("name"));
                subGroup.setGroupId(res.getInt("idGroup"));

                subGroupList.add(subGroup);
            }
            statement.close();
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subGroupList;
    }

    @Override
    public List<SubGroupEntity> getBuyName(String subGroupName) {
        List<SubGroupEntity> subGroupList = new ArrayList<SubGroupEntity>();

        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM " + SubGroupEntity.getNameSubGroupTable() + " WHERE name = '" + subGroupName + "'");

            while (res.next()) {
                SubGroupEntity subGroup = new SubGroupEntity();
                subGroup.setSubGroupId(res.getInt("id"));
                subGroup.setSubGroupName(res.getString("name"));
                subGroup.setGroupId(res.getInt("idGroup"));

                subGroupList.add(subGroup);
            }
            statement.close();
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subGroupList;
    }

    public List<SubGroupEntity> getBuyGroup(int groupId) {
        List<SubGroupEntity> subGroupList = new ArrayList<SubGroupEntity>();

        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM " + SubGroupEntity.getNameSubGroupTable() + " WHERE idGroup = '" + groupId + "'");

            while (res.next()) {
                SubGroupEntity subGroup = new SubGroupEntity();
                subGroup.setSubGroupId(res.getInt("id"));
                subGroup.setSubGroupName(res.getString("name"));
                subGroup.setGroupId(res.getInt("idGroup"));

                subGroupList.add(subGroup);
            }
            statement.close();
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subGroupList;
    }

    @Override
    public boolean add(SubGroupEntity entity) {
        boolean isInsert = false;

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " + SubGroupEntity.getNameSubGroupTable() + " (name, idGroup) VALUES (?, ?)");

            statement.setString(1, entity.getSubGroupName());
            statement.setInt(2, entity.getGroupId());

            int result = statement.executeUpdate();
            isInsert = true;
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isInsert;
    }

    @Override
    public boolean update(int id, SubGroupEntity entity) {
        boolean isUpdate = false;

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE " + SubGroupEntity.getNameSubGroupTable() +
                            " SET name = '" + entity.getSubGroupName() + "', idGroup = '" + entity.getGroupId() +
                            "' WHERE id = '" + id + "'"
            );
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
            PreparedStatement statement = connection.prepareStatement("DELETE FROM " + SubGroupEntity.getNameSubGroupTable() + " WHERE id = '" + id + "'");
            int result = statement.executeUpdate();
            isDelete = true;
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDelete;
    }
}
