package com.javaacademy.robot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.testng.Assert.assertNotNull;

/**
 * @author Anna Gawda
 * 05.09.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookSearchTestIT {

    @Autowired
    BookSearch bookSearch;

    @Test
    public void shouldNotBeNull() {
        assertNotNull(bookSearch);
    }
}
