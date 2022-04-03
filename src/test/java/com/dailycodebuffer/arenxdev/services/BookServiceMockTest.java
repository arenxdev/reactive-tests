package com.dailycodebuffer.arenxdev.services;

import com.dailycodebuffer.arenxdev.exception.BookException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceMockTest {

    @InjectMocks private BookService bookService;
    @Mock private BookInfoService bookInfoService;
    @Mock private ReviewService reviewService;

    @Test
    void getBooks() {
        when(bookInfoService.getBooks()).thenCallRealMethod();
        when(reviewService.getReviews(anyLong())).thenCallRealMethod();
        var books = bookService.getBooks();

        StepVerifier.create(books)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void getBooksOnError() {
        when(bookInfoService.getBooks()).thenCallRealMethod();
        when(reviewService.getReviews(anyLong()))
                .thenThrow(new IllegalStateException("Exception using test"));
        var books = bookService.getBooks();

        StepVerifier.create(books)
                .expectError(BookException.class)
                .verify();
    }

    @Test
    void getBooksRetry() {
        when(bookInfoService.getBooks()).thenCallRealMethod();
        when(reviewService.getReviews(anyLong()))
                .thenThrow(new IllegalStateException("Exception using test"));
        var books = bookService.getBooksRetry();

        StepVerifier.create(books.log())
                .expectError(BookException.class)
                .verify();
    }

    @Test
    void getBooksRetryWhen() {
        when(bookInfoService.getBooks()).thenCallRealMethod();
        when(reviewService.getReviews(anyLong()))
                .thenThrow(new IllegalStateException("Exception using test"));
        var books = bookService.getBooksRetryWhen();

        StepVerifier.create(books.log())
                .expectError(BookException.class)
                .verify();
    }

}