package com.dailycodebuffer.arenxdev.services;

import com.dailycodebuffer.arenxdev.domain.BookInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class BookInfoService {

    public Flux<BookInfo> getBooks() {
        var books = List.of(
                new BookInfo(1, "Java 8 in Action", "Joshua Bloch", "10000"),
                new BookInfo(2, "Reactive Programming with Spring Boot", "Mark Paluch", "10001"),
                new BookInfo(3, "Reactive Programming with Spring Cloud", "Mark Paluch", "10005")
        );
        return Flux.fromIterable(books);
    }

    public Mono<BookInfo> getBookById(long id) {
        return Mono.just(new BookInfo(id, "Java 8 in Action", "Joshua Bloch", "10000"));
    }

}
