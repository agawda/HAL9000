package com.javaacademy.crawler.common.model;

import com.javaacademy.crawler.common.interfaces.Book;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author devas
 * @since 24.08.17
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class BookModel implements Book {

    private Long industryIdentifier;
    private String title;
    private String subtitle;
    private List<String> authors;
    private List<String> categories;
    private String canonicalVolumeLink;
    private String saleability;
    private String smallThumbnail;
    private double listPriceAmount;
    private double retailPriceAmount;
    private String listPriceCurrencyCode;
    private String retailPriceCurrencyCode;

    public static class Builder {
        // Required parameters
        private Long industryIdentifier;
        private String title;
        private List<String> authors;
        private List<String> categories;
        private String canonicalVolumeLink;
        private String smallThumbnail;
        private double listPriceAmount;
        private double retailPriceAmount;

        // Optional parameters - initialized to default values
        private String subtitle = "";
        private String saleability = "FOR_SALE";
        private String listPriceCurrencyCode = "PLN";
        private String retailPriceCurrencyCode = "PLN";

        public Builder(Long industryIdentifier, String title, List<String> authors, List<String> categories,
                                String canonicalVolumeLink, String smallThumbnail,
                                double listPriceAmount, double retailPriceAmount) {
            this.industryIdentifier = industryIdentifier;
            this.title = title;
            this.authors = authors;
            this.categories = categories;
            this.canonicalVolumeLink = canonicalVolumeLink;
            this.smallThumbnail = smallThumbnail;
            this.listPriceAmount = listPriceAmount;
            this.retailPriceAmount = retailPriceAmount;
        }

        public Builder subtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public Builder setSaleability(String saleability) {
            this.saleability = saleability;
            return this;
        }

        public Builder setListPriceCurrencyCode(String listPriceCurrencyCode) {
            this.listPriceCurrencyCode = listPriceCurrencyCode;
            return this;
        }

        public Builder setRetailPriceCurrencyCode(String retailPriceCurrencyCode) {
            this.retailPriceCurrencyCode = retailPriceCurrencyCode;
            return this;
        }

        public BookModel build() {
            return new BookModel(this);
        }
    }

    public BookModel() {
    }

    public BookModel(Builder builder) {
        industryIdentifier = builder.industryIdentifier;
        title = builder.title;
        subtitle = builder.subtitle;
        authors = builder.authors;
        categories = builder.categories;
        canonicalVolumeLink = builder.canonicalVolumeLink;
        saleability = builder.saleability;
        smallThumbnail = builder.smallThumbnail;
        listPriceAmount = builder.listPriceAmount;
        retailPriceAmount = builder.retailPriceAmount;
        listPriceCurrencyCode = builder.listPriceCurrencyCode;
        retailPriceCurrencyCode = builder.retailPriceCurrencyCode;
    }
}
