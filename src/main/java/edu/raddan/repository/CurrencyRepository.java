package edu.raddan.repository;

import edu.raddan.entity.Currency;
import edu.raddan.repository.framework.ListCrudRepository;

import java.sql.SQLException;
import java.util.Optional;

public interface CurrencyRepository extends ListCrudRepository<Currency> {
    Optional<Currency> findByCode(String code) throws SQLException;
}
