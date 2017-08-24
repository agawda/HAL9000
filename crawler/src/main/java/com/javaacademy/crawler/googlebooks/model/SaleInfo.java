package com.javaacademy.crawler.googlebooks.model;

/**
 * @author devas
 * @since 24.08.17
 */
public class SaleInfo {

    private String saleability;
    private Price listPrice; // normal price
    private Price retailPrice; // price with discount

    public String getSaleability() {
        return saleability;
    }

    public void setSaleability(String saleability) {
        this.saleability = saleability;
    }

    public Price getListPrice() {
        return listPrice;
    }

    public void setListPrice(Price listPrice) {
        this.listPrice = listPrice;
    }

    public Price getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Price retailPrice) {
        this.retailPrice = retailPrice;
    }

    @Override
    public String toString() {
        return "SaleInfo{" +
                "saleability='" + saleability + '\'' +
                ", listPrice=" + listPrice +
                ", retailPrice=" + retailPrice +
                '}';
    }
}
