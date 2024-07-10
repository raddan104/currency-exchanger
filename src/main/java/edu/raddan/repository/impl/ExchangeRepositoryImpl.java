package edu.raddan.repository.impl;

import edu.raddan.entity.ExchangeRate;
import edu.raddan.repository.ExchangeRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ExchangeRepositoryImpl implements ExchangeRepository {

    @Override
    public List<ExchangeRate> findByCodesWithUsdBase(String baseCurrencyCode, String targetCurrencyCode) throws SQLException {
        return List.of();
    }

    @Override
    public Optional<ExchangeRate> findByCodes(String baseCurrencyCode, String targetCurrencyCode) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<ExchangeRate> findById(Long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<ExchangeRate> findAll() throws SQLException {
        return List.of();
    }

    @Override
    public Long save(ExchangeRate entity) throws SQLException {
        return 0L;
    }

    @Override
    public void update(ExchangeRate entity) throws SQLException {

    }

    @Override
    public void delete(Long id) throws SQLException {

    }

}
