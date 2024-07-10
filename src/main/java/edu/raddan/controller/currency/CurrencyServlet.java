package edu.raddan.controller.currency;

import edu.raddan.controller.BaseServlet;
import edu.raddan.entity.Currency;
import edu.raddan.service.CurrencyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "CurrencyServlet", value = {"/currency/*"})
public class CurrencyServlet extends BaseServlet {
    private final CurrencyService currencyService = new CurrencyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Currency currency = currencyService.getCurrencyByCode(req, resp);

        if (currency != null) {
            writeJsonResponse(resp, currency);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Currency not found.");
        }
    }
}
