package edu.raddan.repository.impl;

import edu.raddan.entity.Currency;
import edu.raddan.entity.ExchangeRate;
import edu.raddan.repository.ExchangeRepository;
import edu.raddan.utils.SQLHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExchangeRepositoryImpl implements ExchangeRepository {
    private static final Logger LOGGER = Logger.getLogger(ExchangeRepositoryImpl.class.getName());
    private final SQLHandler dataSource = SQLHandler.getInstance();

    @Override
    public Optional<ExchangeRate> findById(Long id) throws SQLException {
        final String query =
                """
                    SELECT
                        er.id AS id,
                        er.BaseCurrencyId AS base_id,
                        bc.code AS base_code,
                        bc.fullname AS base_name,
                        bc.sign AS base_sign,
                        er.TargetCurrencyId AS target_id,
                        tc.code AS target_code,
                        tc.fullname AS target_name,
                        tc.sign AS target_sign,
                        er.rate AS rate
                    FROM exchange_rates er
                    JOIN currencies bc ON er.BaseCurrencyId = bc.id
                    JOIN currencies tc ON er.TargetCurrencyId = tc.id
                    WHERE er.id = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(getExchangeRate(resultSet));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding exchange rate by id: " + id, e);
            throw e;
        }
    }

    @Override
    public List<ExchangeRate> findAll() throws SQLException {
        final String query =
                """
                    SELECT
                        er.id AS id,
                        er.BaseCurrencyId AS base_id,
                        bc.code AS base_code,
                        bc.fullname AS base_name,
                        bc.sign AS base_sign,
                        er.TargetCurrencyId AS target_id,
                        tc.code AS target_code,
                        tc.fullname AS target_name,
                        tc.sign AS target_sign,
                        er.rate AS rate
                    FROM exchange_rates er
                    JOIN currencies bc ON er.BaseCurrencyId = bc.id
                    JOIN currencies tc ON er.TargetCurrencyId = tc.id
                    ORDER BY er.id
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            List<ExchangeRate> exchangeRatesList = new ArrayList<>();
            while (resultSet.next()) {
                exchangeRatesList.add(getExchangeRate(resultSet));
            }
            return exchangeRatesList;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding all exchange rates", e);
            throw e;
        }
    }


    @Override
    public Long save(ExchangeRate entity) throws SQLException {
        final String query = "INSERT INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, rate) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

                statement.setLong(1, entity.getBaseCurrency().getId());
                statement.setLong(2, entity.getTargetCurrency().getId());
                statement.setBigDecimal(3, entity.getRate());

                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long savedId = generatedKeys.getLong(1);
                        connection.commit();
                        return savedId;
                    } else {
                        connection.rollback();
                        throw new SQLException("Failed to retrieve generated keys for saved exchange rate.");
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving exchange rate: " + entity, e);
            throw e;
        }
    }

    @Override
    public void update(ExchangeRate entity) throws SQLException {
        final String query =
                "UPDATE exchange_rates " +
                        "SET BaseCurrencyId = ?, TargetCurrencyId = ?, rate = ? " +
                        "WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, entity.getBaseCurrency().getId());
            statement.setLong(2, entity.getTargetCurrency().getId());
            statement.setBigDecimal(3, entity.getRate());
            statement.setLong(4, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating exchange rate: " + entity, e);
            throw e;
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM exchange_rates WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting exchange rate with id: " + id, e);
            throw e;
        }
    }

    @Override
    public Optional<ExchangeRate> findByCodes(String baseCurrencyCode, String targetCurrencyCode) throws SQLException {
        final String query =
                """
                    SELECT
                        er.id AS id,
                        bc.id AS base_id,
                        bc.code AS base_code,
                        bc.fullname AS base_name,
                        bc.sign AS base_sign,
                        tc.id AS target_id,
                        tc.code AS target_code,
                        tc.fullname AS target_name,
                        tc.sign AS target_sign,
                        er.rate AS rate
                    FROM exchange_rates er
                    JOIN currencies bc ON er.BaseCurrencyId = bc.id
                    JOIN currencies tc ON er.TargetCurrencyId = tc.id
                    WHERE (
                        BaseCurrencyId = (SELECT c.id FROM currencies c WHERE c.code = ?) AND
                        TargetCurrencyId = (SELECT c2.id FROM currencies c2 WHERE c2.code = ?)
                    )
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, baseCurrencyCode);
            statement.setString(2, targetCurrencyCode);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(getExchangeRate(resultSet));
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding exchange rate by codes: " + baseCurrencyCode + ", " + targetCurrencyCode, e);
            throw e;
        }
    }

    @Override
    public List<ExchangeRate> findByCodesWithUsdBase(String baseCurrencyCode, String targetCurrencyCode) throws SQLException {
        final String query =
                """
                    SELECT
                        er.id AS id,
                        bc.id AS base_id,
                        bc.code AS base_code,
                        bc.fullname AS base_name,
                        bc.sign AS base_sign,
                        tc.id AS target_id,
                        tc.code AS target_code,
                        tc.fullname AS target_name,
                        tc.sign AS target_sign,
                        er.rate AS rate
                    FROM exchange_rates er
                    JOIN currencies bc ON er.BaseCurrencyId = bc.id
                    JOIN currencies tc ON er.TargetCurrencyId = tc.id
                    WHERE (
                        BaseCurrencyId = (SELECT c.id FROM currencies c WHERE c.code = 'USD') AND
                        TargetCurrencyId = (SELECT c2.id FROM currencies c2 WHERE c2.code = ?) OR
                        TargetCurrencyId = (SELECT c3.id FROM currencies c3 WHERE c3.code = ?)
                    )
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, baseCurrencyCode);
            statement.setString(2, targetCurrencyCode);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<ExchangeRate> exchangeRatesList = new ArrayList<>();
                while (resultSet.next()) {
                    exchangeRatesList.add(getExchangeRate(resultSet));
                }
                return exchangeRatesList;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding exchange rates by codes with USD base: " + baseCurrencyCode + ", " + targetCurrencyCode, e);
            throw e;
        }
    }

    private static ExchangeRate getExchangeRate(ResultSet resultSet) throws SQLException {
        return new ExchangeRate(
                resultSet.getLong("id"),
                new Currency(
                        resultSet.getLong("base_id"),
                        resultSet.getString("base_code"),
                        resultSet.getString("base_name"),
                        resultSet.getString("base_sign")
                ),
                new Currency(
                        resultSet.getLong("target_id"),
                        resultSet.getString("target_code"),
                        resultSet.getString("target_name"),
                        resultSet.getString("target_sign")
                ),
                resultSet.getBigDecimal("rate")
        );
    }
}
