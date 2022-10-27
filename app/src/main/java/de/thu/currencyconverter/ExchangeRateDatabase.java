package de.thu.currencyconverter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExchangeRateDatabase {
    // Exchange rates to EURO - price for 1 Euro
    private final static ExchangeRate[] RATES = {
            new ExchangeRate("EUR", 1.0),
            new ExchangeRate("USD", 1.0845),
            new ExchangeRate("JPY", 130.02),
            new ExchangeRate("BGN", 1.9558),
            new ExchangeRate("CZK", 27.473),
            new ExchangeRate("DKK", 7.4690),
            new ExchangeRate("GBP", 0.73280),
            new ExchangeRate("HUF", 299.83),
            new ExchangeRate("PLN", 4.0938),
            new ExchangeRate("RON", 4.4050),
            new ExchangeRate("SEK", 9.3207),
            new ExchangeRate("CHF", 1.0439),
            new ExchangeRate("ISK", 141.10),
            new ExchangeRate("NOK", 8.6545),
            new ExchangeRate("HRK", 7.6448),
            new ExchangeRate("TRY", 2.8265),
            new ExchangeRate("AUD", 1.4158),
            new ExchangeRate("BRL", 3.5616),
            new ExchangeRate("CAD", 1.3709),
            new ExchangeRate("CNY", 6.7324),
            new ExchangeRate("HKD", 8.4100),
            new ExchangeRate("IDR", 14172.71),
            new ExchangeRate("ILS", 4.3019),
            new ExchangeRate("INR", 67.9180),
            new ExchangeRate("KRW", 1201.04),
            new ExchangeRate("MXN", 16.5321),
            new ExchangeRate("MYR", 4.0246),
            new ExchangeRate("NZD", 1.4417),
            new ExchangeRate("PHP", 48.527),
            new ExchangeRate("SGD", 1.4898),
            new ExchangeRate("THB", 35.328),
            new ExchangeRate("ZAR", 13.1446)
    };

    private final static Map<String, Double> CURRENCIES_MAP = new HashMap<>();

    private final static String[] CURRENCIES_LIST;

    static {
        for (ExchangeRate r : RATES) {
            CURRENCIES_MAP.put(r.getCurrencyName(), r.getRateForOneEuro());
        }
        CURRENCIES_LIST = new String[CURRENCIES_MAP.size()];

        CURRENCIES_MAP.keySet().toArray(CURRENCIES_LIST);
        Arrays.sort(CURRENCIES_LIST);

    }

    /**
     * Returns list of currency names
     */

    public static String[] getCurrencies() {
        return CURRENCIES_LIST;
    }

    /**
     * Gets exchange rate for currency (equivalent for one Euro)
     */

    public static double getExchangeRate(String currency) {
        return CURRENCIES_MAP.get(currency);
    }

    /**
     * Converts a value from a currency to another one
     *
     * @return converted value
     */
    public static double convert(double value, String currencyFrom, String currencyTo) {
        return value / CURRENCIES_MAP.get(currencyFrom) * CURRENCIES_MAP.get(currencyTo);
    }

    public ExchangeRate[] getExchangeRates() {
        return RATES;
    }
}
