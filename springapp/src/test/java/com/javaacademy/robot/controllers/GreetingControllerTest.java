package com.javaacademy.robot.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    public void shouldReturnStatusOkForBooksMapping() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(greetingController).build();
        this.mockMvc.perform(get("/books")
                .param("id", "")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturnStatusOkForSortTitleMapping() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(greetingController).build();
        this.mockMvc.perform(get("/sort")
                .param("sorting", "title")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatusOkForSortRegularPriceMapping() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(greetingController).build();
        this.mockMvc.perform(get("/sort")
                .param("sorting", "regularPrice")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatusOkForSortNewPriceMapping() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(greetingController).build();
        this.mockMvc.perform(get("/sort")
                .param("sorting", "newPrice")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatusOkForSearchMapping() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(greetingController).build();
        this.mockMvc.perform(post("/search")
                .param("content", "")
                .accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk());
    }
}