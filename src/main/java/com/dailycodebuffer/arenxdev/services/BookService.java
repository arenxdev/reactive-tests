package com.dailycodebuffer.arenxdev.services;

import com.dailycodebuffer.arenxdev.domain.Book;
import com.dailycodebuffer.arenxdev.exception.BookException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;

@Slf4j
public class BookService {

    private final BookInfoService bookInfoService;
    private final ReviewService reviewService;

    public BookService(BookInfoService bookInfoService, ReviewService reviewService) {
        this.bookInfoService = bookInfoService;
        this.reviewService = reviewService;
    }

    public Flux<Book> getBooks() {
        return bookInfoService.getBooks()
                .flatMap(bookInfo -> reviewService
                        .getReviews(bookInfo.getBookId()).collectList()
                        .map(review -> new Book(bookInfo, review)))
                .onErrorMap(e -> {
                    log.error("Exception is :", e);
                    return new BookException("Exception occurred while fetching books");
                });
    }

    public Flux<Book> getBooksRetry() {
        return bookInfoService.getBooks()
                .flatMap(bookInfo -> reviewService
                        .getReviews(bookInfo.getBookId()).collectList()
                        .map(review -> new Book(bookInfo, review)))
                .onErrorMap(e -> {
                    log.error("Exception is :" + e);
                    return new BookException("Exception occurred while fetching books");
                })
                .retry(1);
    }

    public Flux<Book> getBooksRetryWhen() {
        return bookInfoService.getBooks()
                .flatMap(bookInfo -> reviewService
                        .getReviews(bookInfo.getBookId()).collectList()
                        .map(review -> new Book(bookInfo, review)))
                .onErrorMap(e -> {
                    log.error("Exception is :" + e);
                    return new BookException("Exception occurred while fetching books");
                })
                .retryWhen(getRetryExpect());
    }

    private RetryBackoffSpec getRetryExpect() {
        return Retry.backoff(3, Duration.ofSeconds(1))
                .filter(BookException.class::isInstance)
                .onRetryExhaustedThrow(
                        (retryBackoffSpec, retrySignal) -> Exceptions.propagate(retrySignal.failure()));
    }

    public Mono<Book> getBookById(long bookId) {
        return Mono.zip(bookInfoService.getBookById(bookId),
                        reviewService.getReviews(bookId).collectList())
                .map(tuple -> new Book(tuple.getT1(), tuple.getT2()));
    }

    public Mono<Book> getBookById2(long bookId) {
        return bookInfoService.getBookById(bookId)
                .zipWith(reviewService.getReviews(bookId).collectList(), Book::new);
    }

}
