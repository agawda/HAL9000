package com.javaacademy.robot.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author Anna Gawda
 * 05.09.2017
 */
@RunWith(SpringRunner.class)
@WebMvcTest(GreetingController.class)
@SpringBootTest
public class GreetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    
}