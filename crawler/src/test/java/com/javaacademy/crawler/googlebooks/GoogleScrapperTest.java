package com.javaacademy.crawler.googlebooks;

import com.javaacademy.crawler.common.BookAddingCallback;
import com.javaacademy.crawler.common.CustomCallback;
import com.javaacademy.crawler.common.RequestStatus;
import com.javaacademy.crawler.common.interfaces.Book;
import com.javaacademy.crawler.googlebooks.controllers.Controller;
import com.javaacademy.crawler.googlebooks.model.TotalItemsWrapper;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test
public class GoogleScrapperTest {

    @Test
    public void getNumberOfBooksAndStartCollectionTest() {
        GoogleScrapper googleScrapper = new GoogleScrapper();
        Controller controller = mock(Controller.class);
        Consumer consumer = mock(Consumer.class);
        googleScrapper.controller = controller;
        googleScrapper.getNumberOfBooksAndStartCollection(consumer);
        verify(controller, times(1))
                .getHowManyBooksThereAre(new CustomCallback<TotalItemsWrapper>(consumer));
    }

    @Test
    public void collectAllBooksFromGoogleTest() {
        GoogleScrapper googleScrapper = new GoogleScrapper();
        Controller controller = mock(Controller.class);
        googleScrapper.controller = controller;
        GoogleScrapper.sleepTime = 1;
        GoogleScrapper.maxValue = 90;
        int numOfBooks = 100;
        googleScrapper.collectAllBooksFromGoogle(numOfBooks);
        verify(controller, times(3)).getLimitedNumberBooksFromGoogle(any(), anyByte(), anyByte());
        assertEquals(googleScrapper.callbacks.size(), 3);
    }

    @Test
    public void areAllCallbacksDoneTest() {
        GoogleScrapper googleScrapper = new GoogleScrapper();
        googleScrapper.isLoopDone = true;
        googleScrapper.callbacks = getBookCallbacksSet(3);
        boolean result = googleScrapper.areAllCallbacksDone();
        assertTrue(result);
    }

    @Test
    public void getBooksTest() {
        GoogleScrapper googleScrapper = new GoogleScrapper();
        Set<Book> books = mock(Set.class);
        googleScrapper.books = books;
        assertEquals(googleScrapper.getBooks(), books);
    }

    @Test
    public void runScrappingTest() {
        GoogleScrapper googleScrapper = new GoogleScrapper();
        Controller controller = mock(Controller.class);
        googleScrapper.controller = controller;
        googleScrapper.runScrapping();
        verify(controller, times(1)).getHowManyBooksThereAre(any());
    }

    private Set<BookAddingCallback> getBookCallbacksSet(int number) {
        Set<BookAddingCallback> callbacks = new HashSet<>();
        for (int i = 0; i < number; i++) {
            BookAddingCallback c = mock(BookAddingCallback.class);
            when(c.getRequestStatus()).thenReturn(RequestStatus.COMPLETED);
            callbacks.add(c);
        }
        assertEquals(number, callbacks.size());
        return callbacks;
    }


}