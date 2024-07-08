package edu.raddan.repository.framework;

import java.sql.SQLException;
import java.util.Optional;

public interface CrudRepository<T> {

    Optional<T> findById(Long id) throws SQLException;

    Long save(T entity) throws SQLException;

    void update(T entity) throws SQLException;

    void delete(Long id) throws SQLException;
}
