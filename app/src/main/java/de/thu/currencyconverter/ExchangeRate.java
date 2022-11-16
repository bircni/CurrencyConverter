package de.thu.currencyconverter;

public class ExchangeRate {
    private final String currencyName;
    public double rateForOneEuro;
    private final String capital;

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
