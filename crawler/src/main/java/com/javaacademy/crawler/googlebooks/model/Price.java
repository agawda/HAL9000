package com.javaacademy.crawler.googlebooks.model;

/**
 * @author devas
 * @since 24.08.17
 */
public class Price {

    private double amount;
    private String currencyCode;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return "Price{" +
                "amount=" + amount +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
