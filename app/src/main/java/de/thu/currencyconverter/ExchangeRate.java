package de.thu.currencyconverter;

/**
 * This class represents an exchange rate.
 */
public class ExchangeRate {
    private final String currencyName;
    public double rateForOneEuro;
    private final String capital;

    /**
     * Constructor for the ExchangeRate class.
     *
     * @param currencyName The name of the currency.
     * @param rateForOneEuro The exchange rate for one euro.
     * @param capital The capital of the country.
     */
    public ExchangeRate(String currencyName, String capital, double rateForOneEuro) {
        this.currencyName = currencyName;
        this.rateForOneEuro = rateForOneEuro;
        this.capital = capital;
    }

    /**
     * @return the currencyName
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * @return the capital
     */
    public String getCapital() {
        return capital;
    }

    /**
     * @return the rateForOneEuro
     */
    public double getRateForOneEuro() {
        return rateForOneEuro;
    }
}
