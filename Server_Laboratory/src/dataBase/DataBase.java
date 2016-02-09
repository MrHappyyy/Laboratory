package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    private Connection connection;

    public void createDateBase(String name) {

        try {
            connection = null;
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + name);

            ProductDAO productDAO = new ProductDAO(connection);
            productDAO.createTableDataBase();
            GroupDAO groupDAO = new GroupDAO(connection);
            groupDAO.createTableDataBase();
            SubGroupDAO subGroupDAO = new SubGroupDAO(connection);
            subGroupDAO.createTableDataBase();
            StatisticsDAO statisticsDAO = new StatisticsDAO(connection);
            statisticsDAO.createTableDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
