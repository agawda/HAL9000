package com.javaacademy.crawler.googlebooks.model;

/**
 * @author devas
 * @since 24.08.17
 */
public class BookItem {

    private VolumeInfo volumeInfo;
    private SaleInfo saleInfo;

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public SaleInfo getSaleInfo() {
        return saleInfo;
    }

    public void setSaleInfo(SaleInfo saleInfo) {
        this.saleInfo = saleInfo;
    }

    @Override
    public String toString() {
        return "BookItem{" +
                "volumeInfo=" + volumeInfo +
                ", saleInfo=" + saleInfo +
                '}';
    }
}
