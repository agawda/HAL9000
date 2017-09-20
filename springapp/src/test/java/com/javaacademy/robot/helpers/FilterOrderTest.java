package com.javaacademy.robot.helpers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Anna Gawda
 * 19.09.2017
 */
public class FilterOrderTest {

    @Test
    public void shouldReturnTitleAscending() {
        FilterOrder givenType = FilterOrder.valueOf("ASCENDING");

        assertEquals(FilterOrder.ASCENDING, givenType);
    }

    @Test
    public void shouldReturnTitleDescending() {
        FilterOrder givenType = FilterOrder.valueOf("DESCENDING");

        assertEquals(FilterOrder.DESCENDING, givenType);
    }
}