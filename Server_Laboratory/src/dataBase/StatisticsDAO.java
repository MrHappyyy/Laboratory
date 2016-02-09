package dataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatisticsDAO implements DAO<StatisticsEntity> {
    private Connection connection;

    public StatisticsDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTableDataBase() {

        try {
            PreparedStatement product = connection.prepareStatement(StatisticsEntity.getCreateStatisticsTable());
            int result = product.executeUpdate();
            product.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<StatisticsEntity> getAll() {

        List<StatisticsEntity> statisticsList = new ArrayList<StatisticsEntity>();

        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM " + StatisticsEntity.getNameStatisticsTable());

            while (res.next()) {
                StatisticsEntity statistics = new StatisticsEntity();
                statistics.setId(res.getInt("id"));
                statistics.setName(res.getString("name"));
                statistics.setGroup(res.getString("groupName"));
                statistics.setSubGroup(res.getString("subGroupName"));
                statistics.setPrice(res.getDouble("price"));
                statistics.setAmountSell(res.getDouble("amountSell"));
                statistics.setPriceSell(res.getDouble("priceSell"));
                statistics.setData(res.getString("data"));

                statisticsList.add(statistics);
            }
            statement.close();
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return statisticsList;
    }

    @Override
    public List<StatisticsEntity> getBuyName(String name) {
        return null;
    }

    @Override
    public boolean add(StatisticsEntity entity) {
        boolean isInsert = false;

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " + StatisticsEntity.getNameStatisticsTable() +
                    " (name, groupName, subGroupName, price, amountSell, priceSell, data) VALUES (?, ?, ?, ?, ?, ?, ?)");

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getGroup());
            statement.setString(3, entity.getSubGroup());
            statement.setDouble(4, entity.getPrice());
            statement.setDouble(5, entity.getAmountSell());
            statement.setDouble(6, entity.getPriceSell());
            statement.setString(7, entity.getData());

            int result = statement.executeUpdate();
            isInsert = true;
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isInsert;
    }

    @Override
    public boolean update(int id, StatisticsEntity entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
