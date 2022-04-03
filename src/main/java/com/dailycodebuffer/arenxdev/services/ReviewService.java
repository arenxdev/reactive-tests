package com.dailycodebuffer.arenxdev.services;

import com.dailycodebuffer.arenxdev.domain.Review;
import reactor.core.publisher.Flux;

import java.util.List;

public class ReviewService {

    public Flux<Review> getReviews(long bookId) {
        var reviews = List.of(
                new Review(1, bookId, 9.1, "Great book"),
                new Review(2, bookId, 8.1, "Good book"),
                new Review(3, bookId, 7.1, "Not bad"),
                new Review(4, bookId, 6.1, "Not good"),
                new Review(5, bookId, 5.1, "Bad book"),
                new Review(6, bookId, 4.1, "Very bad book")
        );
        return Flux.fromIterable(reviews);
    }



}
