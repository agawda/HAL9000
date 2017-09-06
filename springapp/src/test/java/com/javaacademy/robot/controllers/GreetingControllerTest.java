package com.javaacademy.robot.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeTest;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Anna Gawda
 * 05.09.2017
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GreetingControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private GreetingController greetingController;

    @Test
    public void shouldReturnStatusOkForBookstoresMapping() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(greetingController).build();
        this.mockMvc.perform(get("/bookstores/?id=Books")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk());
    }

//    @Test
//    public void shouldReturnStatusOkForBooksMapping() throws Exception {
//        mockMvc = MockMvcBuilders.standaloneSetup(greetingController).build();
//        this.mockMvc.perform(get("/books/?id=9781422187265")
//                .sessionAttr("id", 9781422187265L)
//                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
//                .andExpect(status().isOk());
//    }
}