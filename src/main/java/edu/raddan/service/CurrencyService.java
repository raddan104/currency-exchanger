package edu.raddan.service;

import edu.raddan.db.SQLHandler;
import edu.raddan.entity.Currency;
import edu.raddan.repository.CurrencyRepository;
import edu.raddan.repository.impl.CurrencyRepositoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

public class CurrencyService {

    private final CurrencyRepository currencyRepository = new CurrencyRepositoryImpl();

    public Currency getCurrencyByCode(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            String code = request.getPathInfo().replaceAll("/", ""); // {code}
            if (code.equals("/")) {
                response.sendError(SC_BAD_REQUEST, "Missing currency code.");
            }
            return currencyRepository.findByCode(code)
                    .orElseThrow(() -> new IllegalArgumentException("Currency with code: " + code + " not found."));
        } catch (IllegalArgumentException | IOException | SQLException e) {
            System.err.println(e.getMessage());
            response.setStatus(SC_BAD_REQUEST);
            return null;
        }
    }

    public List<Currency> getAllCurrencies(HttpServletRequest req, HttpServletResponse res) {
        try {
            return currencyRepository.findAll();
        } catch (SQLException e) {
            System.err.println("Fail: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
