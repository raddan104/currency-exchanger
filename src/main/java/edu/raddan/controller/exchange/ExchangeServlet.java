package edu.raddan.controller.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.raddan.entity.response.ErrorResponse;
import edu.raddan.entity.response.ExchangeResponse;
import edu.raddan.service.ExchangeService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import static jakarta.servlet.http.HttpServletResponse.*;
import static edu.raddan.utils.Validation.isValidCurrencyCode;

@WebServlet(name = "ExchangeServlet", value = {"/exchange"})
public class ExchangeServlet extends HttpServlet {
    private final ExchangeService exchangeService = new ExchangeService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String baseCurrencyCode = req.getParameter("from");
        String targetCurrencyCode = req.getParameter("to");
        String amountToConvertParam = req.getParameter("amount");

        if (baseCurrencyCode == null || baseCurrencyCode.isBlank()) {
            resp.setStatus(SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse(
                    SC_BAD_REQUEST,
                    "Missing parameter - from"
            ));
            return;
        }
        if (targetCurrencyCode == null || targetCurrencyCode.isBlank()) {
            resp.setStatus(SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse(
                    SC_BAD_REQUEST,
                    "Missing parameter - to"
            ));
            return;
        }
        if (amountToConvertParam == null || amountToConvertParam.isBlank()) {
            resp.setStatus(SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse(
                    SC_BAD_REQUEST,
                    "Missing parameter - amount"
            ));
            return;
        }

        if (!isValidCurrencyCode(baseCurrencyCode)) {
            resp.setStatus(SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse(
                    SC_BAD_REQUEST,
                    "Base currency code must be in ISO 4217 format"
            ));
            return;
        }
        if (!isValidCurrencyCode(targetCurrencyCode)) {
            resp.setStatus(SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse(
                    SC_BAD_REQUEST,
                    "Target currency code must be in ISO 4217 format"
            ));
            return;
        }

        BigDecimal amount;
        try {
            amount = BigDecimal.valueOf(Double.parseDouble(amountToConvertParam));
        } catch (NumberFormatException e) {
            resp.setStatus(SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse(
                    SC_BAD_REQUEST,
                    "Incorrect value of amount parameter"
            ));
            return;
        }

        try {
            ExchangeResponse exchangeResponse = exchangeService.convertCurrency(baseCurrencyCode, targetCurrencyCode, amount);
            objectMapper.writeValue(resp.getWriter(), exchangeResponse);

        } catch (SQLException e) {
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse(
                    SC_INTERNAL_SERVER_ERROR,
                    "Something happened with the database, try again later!"
            ));
        } catch (NoSuchElementException e) {
            resp.setStatus(SC_NOT_FOUND);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse(
                    SC_NOT_FOUND,
                    "There is no exchange rate for this currency pair"
            ));
        }
    }
}