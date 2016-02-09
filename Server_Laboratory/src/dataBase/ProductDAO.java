package dataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements DAO<ProductEntity> {
    private Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTableDataBase() {

        try {
            PreparedStatement product = connection.prepareStatement(ProductEntity.getCreateProductTable());
            int result = product.executeUpdate();
            product.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<ProductEntity> getAll() {
        List<ProductEntity> productList = new ArrayList<ProductEntity>();

        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM " + ProductEntity.getNameProductTable());

            while (res.next()) {
                ProductEntity product = new ProductEntity();
                product.setProductId(res.getInt("id"));
                product.setProductName(res.getString("name"));
                product.setSubGroupId(res.getInt("idSubGroup"));
                product.setGroupId(res.getInt("idGroup"));
                product.setAmount(res.getDouble("amount"));
                product.setPrice(res.getDouble("price"));

                productList.add(product);
            }
            statement.close();
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public List<ProductEntity> getBuyName(String productName) {
        List<ProductEntity> productList = new ArrayList<ProductEntity>();

        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM " + ProductEntity.getNameProductTable() + " WHERE name = '" + productName + "'");

            while (res.next()) {
                ProductEntity product = new ProductEntity();
                product.setProductId(res.getInt("id"));
                product.setProductName(res.getString("name"));
                product.setSubGroupId(res.getInt("idSubGroup"));
                product.setGroupId(res.getInt("idGroup"));
                product.setAmount(res.getDouble("amount"));
                product.setPrice(res.getDouble("price"));

                productList.add(product);
            }
            statement.close();
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<ProductEntity> getBuyGroup(int groupId) {
        List<ProductEntity> productList = new ArrayList<ProductEntity>();

        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM " + ProductEntity.getNameProductTable() + " WHERE idGroup = '" + groupId + "'");

            while (res.next()) {
                ProductEntity product = new ProductEntity();
                product.setProductId(res.getInt("id"));
                product.setProductName(res.getString("name"));
                product.setSubGroupId(res.getInt("idSubGroup"));
                product.setGroupId(res.getInt("idGroup"));
                product.setAmount(res.getDouble("amount"));
                product.setPrice(res.getDouble("price"));

                productList.add(product);
            }
            statement.close();
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<ProductEntity> getBuySubGroup(int subGroupId) {
        List<ProductEntity> productList = new ArrayList<ProductEntity>();

        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM " + ProductEntity.getNameProductTable() + " WHERE idSubGroup = '" + subGroupId + "'");

            while (res.next()) {
                ProductEntity product = new ProductEntity();
                product.setProductId(res.getInt("id"));
                product.setProductName(res.getString("name"));
                product.setSubGroupId(res.getInt("idSubGroup"));
                product.setGroupId(res.getInt("idGroup"));
                product.setAmount(res.getDouble("amount"));
                product.setPrice(res.getDouble("price"));

                productList.add(product);
            }
            statement.close();
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public boolean add(ProductEntity entity) {
        boolean isInsert = false;

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO " + ProductEntity.getNameProductTable() + " (name, idGroup, idSubGroup, amount, price) VALUES (?, ?, ?, ?, ?)");

            statement.setString(1, entity.getProductName());
            statement.setInt(2, entity.getGroupId());
            statement.setInt(3, entity.getSubGroupId());
            statement.setDouble(4, entity.getAmount());
            statement.setDouble(5, entity.getPrice());

            int result = statement.executeUpdate();
            isInsert = true;
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isInsert;
    }

    @Override
    public boolean update(int id, ProductEntity entity) {
        boolean isUpdate = false;

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE " + ProductEntity.getNameProductTable() +
                    " SET name = '" + entity.getProductName() + "', idGroup = '" + entity.getGroupId() +
                    "', idSubGroup = '" + entity.getSubGroupId() + "', amount = '" + entity.getAmount() +
                    "', price = '" + entity.getPrice() + "' WHERE id = '" + id + "'"
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
            PreparedStatement statement = connection.prepareStatement("DELETE FROM " + ProductEntity.getNameProductTable() + " WHERE id = '" + id + "'");
            int result = statement.executeUpdate();
            isDelete = true;
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDelete;
    }
}
