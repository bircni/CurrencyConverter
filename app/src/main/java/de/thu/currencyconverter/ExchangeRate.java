package de.thu.currencyconverter;

public class ExchangeRate {
    private String currencyName;
    private double rateForOneEuro;

    public ExchangeRate(String currencyName, double rateForOneEuro) {
        this.currencyName = currencyName;
        this.rateForOneEuro = rateForOneEuro;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public double getRateForOneEuro() {
        return rateForOneEuro;
    }
}
