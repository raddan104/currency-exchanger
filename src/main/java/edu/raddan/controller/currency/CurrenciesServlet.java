package edu.raddan.controller.currency;

import edu.raddan.controller.BaseServlet;
import edu.raddan.entity.Currency;
import edu.raddan.service.CurrencyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CurrenciesServlet", value = {"/currencies"})
public class CurrenciesServlet extends BaseServlet {
    private final CurrencyService currencyService = new CurrencyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        List<Currency> currencies = currencyService.getAllCurrencies(req, res);

        if (currencies != null)
            writeJsonResponse(res, currencies);
        else
            res.sendError(HttpServletResponse.SC_NOT_FOUND, "Currencies are blank.");
    }

}
