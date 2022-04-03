package com.dailycodebuffer.arenxdev.services;

import com.dailycodebuffer.arenxdev.domain.Book;
import com.dailycodebuffer.arenxdev.domain.BookInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private final BookInfoService bookInfoService = new BookInfoService();
    private final ReviewService reviewService = new ReviewService();
    private final BookService bookService = new BookService(bookInfoService, reviewService);

    @Test
    void getBooks() {
        var books = bookService.getBooks();
        StepVerifier.create(books.log())
                .assertNext(book -> assertThat(book)
                        .extracting(b -> book.getBookInfo().getTitle(), b -> book.getReviews().size())
                        .contains("Java 8 in Action", 6))
                .assertNext(book -> assertThat(book)
                        .extracting(b -> book.getBookInfo().getTitle(), b -> book.getReviews().size())
                        .contains("Reactive Programming with Spring Boot", 6))
                .assertNext(book -> assertThat(book)
                        .extracting(b -> book.getBookInfo().getTitle(), b -> book.getReviews().size())
                        .contains("Reactive Programming with Spring Cloud", 6))
                .verifyComplete();
    }

    @Test
    void getBookById() {
        var book = bookService.getBookById(1L);
        StepVerifier.create(book.log())
                .assertNext(b1 -> assertThat(b1)
                        .extracting(b2 -> b2.getBookInfo().getTitle(), b2 -> b2.getReviews().size())
                        .contains("Java 8 in Action", 6))
                .verifyComplete();
    }
}