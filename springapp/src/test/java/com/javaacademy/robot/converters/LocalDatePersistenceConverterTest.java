package com.javaacademy.robot.converters;

import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class LocalDatePersistenceConverterTest {

    @Test
    public void testConversion(){
        LocalDatePersistenceConverter localDatePersistenceConverter = new LocalDatePersistenceConverter();
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = localDatePersistenceConverter.convertToDatabaseColumn(localDateTime);
        LocalDateTime result = localDatePersistenceConverter.convertToEntityAttribute(timestamp);
        assertEquals(result, localDateTime);
    }

}