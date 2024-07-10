package edu.raddan.repository.impl;

import edu.raddan.db.SQLHandler;
import edu.raddan.entity.Currency;
import edu.raddan.repository.CurrencyRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyRepositoryImpl implements CurrencyRepository {

    @Override
    public Optional<Currency> findByCode(String code) {
        final String query = "SELECT * FROM currencies WHERE Code = ?";
        try (Connection connection = SQLHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, code);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Currency currency = new Currency();
                    currency.setId(resultSet.getLong("Id"));
                    currency.setCode(resultSet.getString("Code"));
                    currency.setFullName(resultSet.getString("FullName"));
                    currency.setSign(resultSet.getString("Sign"));

                    return Optional.of(currency);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Currency> findById(Long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Currency> findAll() throws SQLException {
        final String query = "SELECT * FROM currencies";
        List<Currency> currencies = new ArrayList<>();

        try (Connection connection = SQLHandler.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Currency currency = new Currency();
                currency.setId(resultSet.getLong("Id"));
                currency.setCode(resultSet.getString("Code"));
                currency.setFullName(resultSet.getString("FullName"));
                currency.setSign(resultSet.getString("Sign"));
                currencies.add(currency);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all currencies: " + e.getMessage());
            throw e;
        }
        return currencies;
    }

    @Override
    public Long save(Currency entity) throws SQLException {
        return 0L;
    }

    @Override
    public void update(Currency entity) throws SQLException {

    }

    @Override
    public void delete(Currency entity) throws SQLException {

    }
}
