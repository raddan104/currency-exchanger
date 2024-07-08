package edu.raddan.repository.framework;

import java.sql.SQLException;
import java.util.List;

public interface ListCrudRepository<T> extends CrudRepository<T> {
    List<T> findAll() throws SQLException;
}

