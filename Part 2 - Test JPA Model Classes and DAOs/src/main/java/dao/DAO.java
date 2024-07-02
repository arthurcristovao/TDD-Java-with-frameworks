package dao;

import java.util.List;

public interface DAO<T> {
    void insert(T obj);
    void delete(int id);
    void update(T obj);
    List<T> list(int limit, int offset);
    T get(int id);
}
