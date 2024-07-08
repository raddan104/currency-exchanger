package edu.raddan.service;

import edu.raddan.entity.ExchangeRate;
import edu.raddan.repository.ExchangeRateRepository;
import edu.raddan.repository.impl.ExchangeRateRepositoryImpl;
import edu.raddan.entity.response.ExchangeResponse;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.math.MathContext.DECIMAL64;
import static java.math.RoundingMode.HALF_EVEN;

public class ExchangeService {
    private final ExchangeRateRepository exchangeRepository = new ExchangeRateRepositoryImpl();

    public ExchangeResponse convertCurrency(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount) throws SQLException, NoSuchElementException {
        ExchangeRate exchangeRate = getExchangeRate(baseCurrencyCode, targetCurrencyCode).orElseThrow();

        BigDecimal convertedAmount = amount.multiply(exchangeRate.getRate()).setScale(2, HALF_EVEN);

        return new ExchangeResponse(
                exchangeRate.getBaseCurrency(),
                exchangeRate.getTargetCurrency(),
                exchangeRate.getRate(),
                amount,
                convertedAmount
        );
    }

    private Optional<ExchangeRate> getExchangeRate(String baseCurrencyCode, String targetCurrencyCode) throws SQLException {
        Optional<ExchangeRate> exchangeRate = getFromDirectExchangeRate(baseCurrencyCode, targetCurrencyCode);

        if (exchangeRate.isEmpty()) {
            exchangeRate = getFromReverseExchangeRate(baseCurrencyCode, targetCurrencyCode);
        }

        if (exchangeRate.isEmpty()) {
            exchangeRate = getFromCrossExchangeRate(baseCurrencyCode, targetCurrencyCode);
        }

        return exchangeRate;
    }

    private Optional<ExchangeRate> getFromDirectExchangeRate(String baseCurrencyCode, String targetCurrencyCode) throws SQLException {
        return exchangeRepository.findByCodes(baseCurrencyCode, targetCurrencyCode);
    }

    private Optional<ExchangeRate> getFromReverseExchangeRate(String baseCurrencyCode, String targetCurrencyCode) throws SQLException {
        Optional<ExchangeRate> exchangeRateOptional = exchangeRepository.findByCodes(targetCurrencyCode, baseCurrencyCode);

        if (exchangeRateOptional.isEmpty()) {
            return Optional.empty();
        }

        ExchangeRate reversedExchangeRate = exchangeRateOptional.get();

        ExchangeRate directExchangeRate = new ExchangeRate(
                reversedExchangeRate.getTargetCurrency(),
                reversedExchangeRate.getBaseCurrency(),
                BigDecimal.ONE.divide(reversedExchangeRate.getRate(), DECIMAL64)
        );

        return Optional.of(directExchangeRate);
    }

    private Optional<ExchangeRate> getFromCrossExchangeRate(String baseCurrencyCode, String targetCurrencyCode) throws SQLException {
        List<ExchangeRate> ratesWithUsdBase = exchangeRepository.findByCodesWithUsdBase(baseCurrencyCode, targetCurrencyCode);

        ExchangeRate usdToBaseExchange = getExchangeForCode(ratesWithUsdBase, baseCurrencyCode);
        ExchangeRate usdToTargetExchange = getExchangeForCode(ratesWithUsdBase, targetCurrencyCode);

        BigDecimal usdToBaseRate = usdToBaseExchange.getRate();
        BigDecimal usdToTargetRate = usdToTargetExchange.getRate();

        BigDecimal baseToTargetRate = usdToTargetRate.divide(usdToBaseRate, DECIMAL64);

        ExchangeRate exchangeRate = new ExchangeRate(
                usdToBaseExchange.getTargetCurrency(),
                usdToTargetExchange.getTargetCurrency(),
                baseToTargetRate
        );

        return Optional.of(exchangeRate);
    }

    private static ExchangeRate getExchangeForCode(List<ExchangeRate> rates, String code) {
        return rates.stream()
                .filter(rate -> rate.getTargetCurrency().getCode().equals(code))
                .findFirst()
                .orElseThrow();
    }
}