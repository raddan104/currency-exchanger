package edu.raddan.repository;

import edu.raddan.entity.Currency;

import java.sql.SQLException;
import java.util.Optional;

public interface CurrencyRepository extends CrudRepository<Currency> {
    Optional<Currency> findByCode(String code) throws SQLException;
}
