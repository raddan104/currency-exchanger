package edu.raddan.repository.impl;

import edu.raddan.db.DatabaseHandler;
import edu.raddan.entity.Currency;
import edu.raddan.repository.CurrencyRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyRepositoryImpl implements CurrencyRepository {

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

    @Override
    public Optional<Currency> findByCode(String code) throws SQLException {
        final String query = "SELECT * FROM currencies WHERE code = ?";

        try (Connection connection = databaseHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, code);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            if (!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(getCurrency(resultSet));
        }
    }

    @Override
    public List<Currency> findAll() throws SQLException {
        final String query = "SELECT * FROM currencies";

        try (Connection connection = databaseHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            List<Currency> currencies = new ArrayList<>();
            while (resultSet.next())
                currencies.add(getCurrency(resultSet));

            return currencies;
        }
    }

    @Override
    public Optional<Currency> findById(Long id) throws SQLException {
        final String query = "SELECT * FROM currencies WHERE id = ?";

        try (Connection connection = databaseHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            if (!resultSet.next())
                return Optional.empty();

            return Optional.of(getCurrency(resultSet));
        }
    }

    @Override
    public Long save(Currency entity) throws SQLException {
        final String query = "INSERT INTO currencies (code, fullname, sign) VALUES (?, ?, ?)";

        try (Connection connection = databaseHandler.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, entity.getCode());
            statement.setString(2, entity.getFullName());
            statement.setString(3, entity.getSign());

            statement.execute();

            ResultSet savedCurrency = statement.getGeneratedKeys();
            savedCurrency.next();
            long savedId = savedCurrency.getLong("id");

            connection.commit();

            return savedId;
        }
    }

    @Override
    public void update(Currency entity) throws SQLException {
        final String query = "UPDATE currencies SET (code, fullname, sign) = (?, ?, ?) WHERE id = ?";

        try (Connection connection = databaseHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, entity.getCode());
            statement.setString(2, entity.getFullName());
            statement.setString(3, entity.getSign());
            statement.setLong(4, entity.getId());

            statement.execute();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String query = "DELETE FROM currencies WHERE id = ?";

        try (Connection connection = databaseHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.execute();
        }
    }

    private static Currency getCurrency(ResultSet resultSet) throws SQLException {
        return new Currency(
                resultSet.getLong("id"),
                resultSet.getString("code"),
                resultSet.getString("fullname"),
                resultSet.getString("sign")
        );
    }
}
