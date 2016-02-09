package dataBase;

import java.util.List;

public interface DAO<T> {
    void createTableDataBase();

    List<T> getAll();
    List<T> getBuyName(String name);

    boolean add(T entity);

    boolean update(int id, T entity);

    boolean delete(int id);
}
