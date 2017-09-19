package com.javaacademy.robot.helpers;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Anna Gawda
 * 19.09.2017
 */
public class FilterTypeTest {

    @Test
    public void shouldReturnTitleAscending() {
        FilterType givenType = FilterType.valueOf("TITLE_ASCENDING");

        assertEquals(FilterType.TITLE_ASCENDING, givenType);
    }

    @Test
    public void shouldReturnTitleDescending() {
        FilterType givenType = FilterType.valueOf("TITLE_DESCENDING");

        assertEquals(FilterType.TITLE_DESCENDING, givenType);
    }

    @Test
    public void shouldReturnAuthorAscending() {
        FilterType givenType = FilterType.valueOf("AUTHORS_ASCENDING");

        assertEquals(FilterType.AUTHORS_ASCENDING, givenType);
    }

    @Test
    public void shouldReturnAuthorDescending() {
        FilterType givenType = FilterType.valueOf("AUTHORS_DESCENDING");

        assertEquals(FilterType.AUTHORS_DESCENDING, givenType);
    }

    @Test
    public void shouldReturnPriceAscending() {
        FilterType givenType = FilterType.valueOf("PRICE_ASCENDING");

        assertEquals(FilterType.PRICE_ASCENDING, givenType);
    }

    @Test
    public void shouldReturnPriceDescending() {
        FilterType givenType = FilterType.valueOf("PRICE_DESCENDING");

        assertEquals(FilterType.PRICE_DESCENDING, givenType);
    }

    @Test
    public void shouldReturnDiscountAscending() {
        FilterType givenType = FilterType.valueOf("DISCOUNT_ASCENDING");

        assertEquals(FilterType.DISCOUNT_ASCENDING, givenType);
    }

    @Test
    public void shouldReturnDiscountDescending() {
        FilterType givenType = FilterType.valueOf("DISCOUNT_DESCENDING");

        assertEquals(FilterType.DISCOUNT_DESCENDING, givenType);
    }

}