package de.thu.currencyconverter;

import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.paperdb.Paper;

public class ExchangeRateDatabase {
    // Exchange rates to EURO - price for 1 Euro
    private static final ExchangeRate[] RATES = {
            new ExchangeRate("EUR", "Bruxelles", 1.0),
            new ExchangeRate("USD", "Washington", 1.0845),
            new ExchangeRate("JPY", "Tokyo", 130.02),
            new ExchangeRate("BGN", "Sofia", 1.9558),
            new ExchangeRate("CZK", "Prague", 27.473),
            new ExchangeRate("DKK", "Copenhagen", 7.4690),
            new ExchangeRate("GBP", "London", 0.73280),
            new ExchangeRate("HUF", "Budapest", 299.83),
            new ExchangeRate("PLN", "Warsaw", 4.0938),
            new ExchangeRate("RON", "Bucharest", 4.4050),
            new ExchangeRate("SEK", "Stockholm", 9.3207),
            new ExchangeRate("CHF", "Bern", 1.0439),
            new ExchangeRate("ISK", "Reykjavik", 141.10),
            new ExchangeRate("NOK", "Oslo", 8.6545),
            new ExchangeRate("HRK", "Zagreb", 7.6448),
            new ExchangeRate("TRY", "Ankara", 2.8265),
            new ExchangeRate("AUD", "Canberra", 1.4158),
            new ExchangeRate("BRL", "Brasilia", 3.5616),
            new ExchangeRate("CAD", "Ottawa", 1.3709),
            new ExchangeRate("CNY", "Beijing", 6.7324),
            new ExchangeRate("HKD", "Hong Kong", 8.4100),
            new ExchangeRate("IDR", "Jakarta", 14172.71),
            new ExchangeRate("ILS", "Jerusalem", 4.3019),
            new ExchangeRate("INR", "New Delhi", 67.9180),
            new ExchangeRate("KRW", "Seoul", 1201.04),
            new ExchangeRate("MXN", "Mexico City", 16.5321),
            new ExchangeRate("MYR", "Kuala Lumpur", 4.0246),
            new ExchangeRate("NZD", "Wellington", 1.4417),
            new ExchangeRate("PHP", "Manila", 48.527),
            new ExchangeRate("SGD", "Singapore", 1.4898),
            new ExchangeRate("THB", "Bangkok", 35.328),
            new ExchangeRate("ZAR", "Cape Town", 13.1446)
    };


    private final static Map<String, ExchangeRate> CURRENCIES_MAP = new HashMap<>();

    private final static String[] CURRENCIES_LIST;

    static {
        for (ExchangeRate r : RATES) {
            CURRENCIES_MAP.put(r.getCurrencyName(), r);
        }
        CURRENCIES_LIST = new String[CURRENCIES_MAP.size()];

        CURRENCIES_MAP.keySet().toArray(CURRENCIES_LIST);
        Arrays.sort(CURRENCIES_LIST);

    }
    public static void initDB() {
        if (!Paper.book().contains("Database")) {
            Paper.book().write("Database", RATES);
        }
    }

    /**
     * Gets exchange rate for currency (equivalent for one Euro)
     */

    public static double getExchangeRate(String currency) {
        return Objects.requireNonNull(CURRENCIES_MAP.get(currency)).getRateForOneEuro();
    }

    public static double getExchangeRatePaper(String currency) {
        ExchangeRate[] rates = Paper.book().read("Database");
        assert rates != null;
        for (ExchangeRate r : rates) {
            if (r.getCurrencyName().equals(currency)) {
                return r.getRateForOneEuro();
            }
        }
        return 0;
    }

    /**
     * Converts a value from a currency to another one
     *
     * @return converted value
     */
    public static double convert(double value, String currencyFrom, String currencyTo) {
        return value / getExchangeRate(currencyFrom) * getExchangeRate(currencyTo);
    }

    //NEW CONVERT
    public static double convertPaper(double value, int currencyFrom, int currencyTo) {
        ExchangeRate[] er = Paper.book().read("Database");
        assert er != null;
        String from = er[currencyFrom].getCurrencyName();
        Log.d("Convert:", from);
        String to = er[currencyTo].getCurrencyName();
        Log.d("Convert", to);
        return value / getExchangeRatePaper(from) * getExchangeRatePaper(to);
    }

    /**
     * Returns list of currency names
     */

    public String[] getCurrencies() {
        return CURRENCIES_LIST;
    }

    public String getCapital(String currency) {
        return Objects.requireNonNull(CURRENCIES_MAP.get(currency)).getCapital();
    }

    public ExchangeRate[] getExchangeRates() {
        return RATES;
    }
}
