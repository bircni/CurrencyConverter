package de.thu.currencyconverter;

public class ExchangeRate {
    private final String currencyName;
    private final double rateForOneEuro;

    public ExchangeRate(String currencyName, double rateForOneEuro) {
        this.currencyName = currencyName;
        this.rateForOneEuro = rateForOneEuro;
    }

    /**
     * @return the currencyName
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * @return the rateForOneEuro
     */
    public double getRateForOneEuro() {
        return rateForOneEuro;
    }
}