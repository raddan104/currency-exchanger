CREATE TABLE IF NOT EXISTS currencies(
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    Code VARCHAR(3) NOT NULL,
    FullName VARCHAR(255) NOT NULL,
    Sign VARCHAR(5) NOT NULL
);

CREATE TABLE IF NOT EXISTS exchange_rates(
    ID INTEGER PRIMARY KEY AUTOINCREMENT,
    BaseCurrencyId INTEGER NOT NULL,
    TargetCurrencyId INTEGER NOT NULL,
    Rate DECIMAL(6) NOT NULL,
    FOREIGN KEY (BaseCurrencyId) REFERENCES currencies(ID),
    FOREIGN KEY (TargetCurrencyId) REFERENCES currencies(ID)
);

INSERT OR IGNORE INTO currencies (Code, FullName, Sign) VALUES ('USD', 'United States Dollar', '$');
INSERT OR IGNORE INTO currencies (Code, FullName, Sign) VALUES ('EUR', 'Euro', '€');
INSERT OR IGNORE INTO currencies (Code, FullName, Sign) VALUES ('JPY', 'Japanese Yen', '¥');
INSERT OR IGNORE INTO currencies (Code, FullName, Sign) VALUES ('GBP', 'British Pound Sterling', '£');
INSERT OR IGNORE INTO currencies (Code, FullName, Sign) VALUES ('AUD', 'Australian Dollar', 'A$');
INSERT OR IGNORE INTO currencies (Code, FullName, Sign) VALUES ('CAD', 'Canadian Dollar', 'C$');
INSERT OR IGNORE INTO currencies (Code, FullName, Sign) VALUES ('CHF', 'Swiss Franc', 'CHF');
INSERT OR IGNORE INTO currencies (Code, FullName, Sign) VALUES ('CNY', 'Chinese Yuan', '¥');
INSERT OR IGNORE INTO currencies (Code, FullName, Sign) VALUES ('SEK', 'Swedish Krona', 'kr');
INSERT OR IGNORE INTO currencies (Code, FullName, Sign) VALUES ('NZD', 'New Zealand Dollar', 'NZ$');

INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (1, 2, 0.923);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (1, 3, 160.9814);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (1, 4, 0.78);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (1, 5, 1.4837);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (1, 6, 1.3635);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (1, 7, 0.8959);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (1, 8, 7.3039);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (1, 9, 10.4974);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (1, 10, 1.6302);

INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (2, 1, 1.0834);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (2, 3, 174.3795);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (2, 4, 0.845);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (2, 5, 1.6074);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (2, 6, 1.4782);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (2, 7, 0.9711);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (2, 8, 7.9067);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (2, 9, 11.3755);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (2, 10, 1.7675);

INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (3, 1, 0.0062);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (3, 2, 0.0057);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (3, 4, 0.0048);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (3, 5, 0.0092);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (3, 6, 0.0085);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (3, 7, 0.0056);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (3, 8, 0.0453);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (3, 9, 0.0652);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (3, 10, 0.0094);

INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (4, 1, 1.2821);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (4, 2, 1.1846);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (4, 3, 210.1428);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (4, 5, 1.9058);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (4, 6, 1.7653);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (4, 7, 1.1493);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (4, 8, 9.3525);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (4, 9, 13.4533);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (4, 10, 2.0887);

INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (5, 1, 0.6742);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (5, 2, 0.6221);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (5, 3, 110.1683);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (5, 4, 0.5248);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (5, 6, 0.926);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (5, 7, 0.6065);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (5, 8, 4.905);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (5, 9, 7.0616);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (5, 10, 1.0962);

INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (6, 1, 0.7334);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (6, 2, 0.6766);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (6, 3, 119.1632);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (6, 4, 0.5682);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (6, 5, 1.080);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (6, 7, 0.6553);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (6, 8, 5.3087);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (6, 9, 7.6468);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (6, 10, 1.1874);

INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (7, 1, 1.1163);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (7, 2, 1.0298);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (7, 3, 181.7927);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (7, 4, 0.8678);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (7, 5, 1.6476);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (7, 6, 1.5251);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (7, 8, 8.0948);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (7, 9, 11.6705);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (7, 10, 1.8123);

INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (8, 1, 0.137);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (8, 2, 0.1265);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (8, 3, 22.4642);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (8, 4, 0.1074);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (8, 5, 0.2031);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (8, 6, 0.188);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (8, 7, 0.1236);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (8, 9, 1.4417);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (8, 10, 0.2235);

INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (9, 1, 0.0952);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (9, 2, 0.088);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (9, 3, 17.4443);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (9, 4, 0.0837);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (9, 5, 0.1592);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (9, 6, 0.1469);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (9, 7, 0.0962);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (9, 8, 0.7843);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (9, 10, 0.1557);

INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (10, 1, 0.6135);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (10, 2, 0.566);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (10, 3, 99.5862);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (10, 4, 0.4791);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (10, 5, 0.9108);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (10, 6, 0.8403);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (10, 7, 0.5511);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (10, 8, 4.4837);
INSERT OR IGNORE INTO exchange_rates (BaseCurrencyId, TargetCurrencyId, Rate) VALUES (10, 9, 6.462);
