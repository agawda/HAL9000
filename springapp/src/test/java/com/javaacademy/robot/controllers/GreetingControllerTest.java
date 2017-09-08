package com.javaacademy.robot.controllers;

import com.javaacademy.robot.model.BookDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
    private String mediaTypeTextUtf8 = "text/html;charset=UTF-8";
    private String urlTemplateSort = "/sort";
    private String sortingString = "sorting";

    private MockMvc mockMvc;

    @Autowired
    private GreetingController greetingController;

    @Test
    public void shouldReturnStatusOkForBookstoresMapping() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(greetingController).build();
        this.mockMvc.perform(get("/bookstores/?id=Books")
                .accept(MediaType.parseMediaType(mediaTypeTextUtf8)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatusOkForBooksMapping() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(greetingController).build();
        this.mockMvc.perform(get(urlTemplateSort)
                .param("id", "")
                .accept(MediaType.parseMediaType(mediaTypeTextUtf8)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturnStatusOkForSortTitleMapping() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(greetingController).build();
        this.mockMvc.perform(get(urlTemplateSort)
                .param(sortingString, "title")
                .accept(MediaType.parseMediaType(mediaTypeTextUtf8)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatusOkForSortRegularPriceMapping() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(greetingController).build();
        this.mockMvc.perform(get(urlTemplateSort)
                .param(sortingString, "regularPrice")
                .accept(MediaType.parseMediaType(mediaTypeTextUtf8)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatusOkForSortNewPriceMapping() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(greetingController).build();
        this.mockMvc.perform(get(urlTemplateSort)
                .param(sortingString, "newPrice")
                .accept(MediaType.parseMediaType(mediaTypeTextUtf8)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatusOkForSearchMapping() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(greetingController).build();
        this.mockMvc.perform(post("/search")
                .param("content", "")
                .accept(MediaType.parseMediaType(mediaTypeTextUtf8)))
                .andExpect(status().isOk());
    }

    @Test
    public void setImageZoomTest() {
        BookDto bookDto = new BookDto();
        String smallThumbnail = "&zoom=5";
        bookDto.setSmallThumbnail(smallThumbnail);
        GreetingController greetingControllerNotAutowired = new GreetingController(null, null);
        int newZoom = 1;
        greetingControllerNotAutowired.setImageZoom(bookDto, newZoom);
        assertTrue(bookDto.getSmallThumbnail().contains("&zoom=" + newZoom));
    }

    @Test
    public void setImageZoomTestWithoutString() {
        BookDto bookDto = new BookDto();
        String smallThumbnail = "";
        bookDto.setSmallThumbnail(smallThumbnail);
        GreetingController greetingControllerNotAutowired = new GreetingController(null, null);
        int newZoom = 1;
        greetingControllerNotAutowired.setImageZoom(bookDto, newZoom);
        assertFalse(bookDto.getSmallThumbnail().contains("&zoom=" + newZoom));
    }
}