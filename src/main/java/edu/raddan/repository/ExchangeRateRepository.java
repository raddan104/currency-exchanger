package edu.raddan.repository;

import edu.raddan.entity.ExchangeRate;
import edu.raddan.repository.framework.ListCrudRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ExchangeRateRepository<T> extends ListCrudRepository<ExchangeRate> {
    List<ExchangeRate> findByCodesWithUsdBase(String baseCurrencyCode, String targetCurrencyCode) throws SQLException;
    Optional<ExchangeRate> findByCodes(String baseCurrencyCode, String targetCurrencyCode) throws SQLException;
}
