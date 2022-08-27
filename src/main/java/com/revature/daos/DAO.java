package com.revature.daos;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    void save(T obj) throws SQLException;
    void update(T obj);
    void delete(String id);
    T getByKey(String id);
    List<T> getAll();
}
