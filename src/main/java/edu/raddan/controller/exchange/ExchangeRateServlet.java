package edu.raddan.controller.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.raddan.entity.ExchangeRate;
import edu.raddan.entity.response.ErrorResponse;
import edu.raddan.repository.ExchangeRepository;
import edu.raddan.repository.impl.ExchangeRepositoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

import static jakarta.servlet.http.HttpServletResponse.*;
import static edu.raddan.utils.Validation.isValidCurrencyCode;

@WebServlet(name = "ExchangeRateServlet", value = {"/exchangeRate/*"})
public class ExchangeRateServlet extends HttpServlet {
    private final ExchangeRepository exchangeRepository = new ExchangeRepositoryImpl();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getMethod().equalsIgnoreCase("PATCH")) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String url = req.getPathInfo().replaceAll("/", "");

        if (url.length() != 6) {
            resp.setStatus(SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse(
                    SC_BAD_REQUEST,
                    "Currency codes are either not provided or provided in an incorrect format"
            ));
            return;
        }

        String baseCurrencyCode = url.substring(0, 3);
        String targetCurrencyCode = url.substring(3);

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

        try {
            Optional<ExchangeRate> exchangeRateOptional = exchangeRepository.findByCodes(baseCurrencyCode, targetCurrencyCode);

            if (exchangeRateOptional.isEmpty()) {
                resp.setStatus(SC_NOT_FOUND);
                objectMapper.writeValue(resp.getWriter(), new ErrorResponse(
                        SC_NOT_FOUND,
                        "There is no exchange rate for this currency pair"
                ));
                return;
            }

            objectMapper.writeValue(resp.getWriter(), exchangeRateOptional.get());

        } catch (SQLException e) {
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse(
                    SC_INTERNAL_SERVER_ERROR,
                    "Something happened with the database, try again later!"
            ));
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getPathInfo().replaceAll("/", "");

        if (url.length() != 6) {
            resp.setStatus(SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse(
                    SC_BAD_REQUEST,
                    "Currency codes are either not provided or provided in an incorrect format"
            ));
            return;
        }

        String parameter = req.getReader().readLine();
        if (parameter == null || !parameter.contains("rate")) {
            resp.setStatus(SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse(
                    SC_BAD_REQUEST,
                    "Missing required parameter rate"
            ));
            return;
        }

        String baseCurrencyCode = url.substring(0, 3);
        String targetCurrencyCode = url.substring(3);
        String paramRateValue = parameter.replace("rate=", "");

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

        BigDecimal rate;
        try {
            rate = BigDecimal.valueOf(Double.parseDouble(paramRateValue));
        } catch (NumberFormatException e) {
            resp.setStatus(SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse(
                    SC_BAD_REQUEST,
                    "Incorrect value of rate parameter"
            ));
            return;
        }

        try {
            Optional<ExchangeRate> exchangeRateOptional = exchangeRepository.findByCodes(baseCurrencyCode, targetCurrencyCode);

            if (exchangeRateOptional.isEmpty()) {
                resp.setStatus(SC_NOT_FOUND);
                objectMapper.writeValue(resp.getWriter(), new ErrorResponse(
                        SC_NOT_FOUND,
                        "There is no exchange rate for this currency pair"
                ));
                return;
            }

            ExchangeRate exchangeRate = exchangeRateOptional.get();
            exchangeRate.setRate(rate);
            exchangeRepository.update(exchangeRate);

            objectMapper.writeValue(resp.getWriter(), exchangeRate);

        } catch (SQLException e) {
            resp.setStatus(SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), new ErrorResponse(
                    SC_INTERNAL_SERVER_ERROR,
                    "Something happened with the database, try again later!"
            ));
        }
    }
}
