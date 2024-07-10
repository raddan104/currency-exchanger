package edu.raddan.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    Optional<T> findById(Long id) throws SQLException;
    List<T> findAll() throws SQLException;
    Long save(T entity) throws SQLException;
    void update(T entity) throws SQLException;
    void delete(T entity) throws SQLException;
}
