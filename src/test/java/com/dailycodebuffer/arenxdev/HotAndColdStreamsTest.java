package com.dailycodebuffer.arenxdev;

import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotAndColdStreamsTest {

    @Test
    public void coldStreamTest() {
        var numbers = Flux.range(1, 10);
        numbers.subscribe(integer -> System.out.println("Subscriber 1: " + integer));
        numbers.subscribe(integer -> System.out.println("Subscriber 1: " + integer));
    }

    @Test
    public void hotStreamTest() throws InterruptedException {
        var numbers = Flux.range(1, 100)
                .delayElements(Duration.ofSeconds(1));

        ConnectableFlux<Integer> publisher = numbers.publish();
        publisher.connect();

        Thread.sleep(4000);
        Disposable subscribe1 = publisher.subscribe(integer -> System.out.println("Subscriber 1: " + integer));
        Thread.sleep(4000);
        Disposable subscribe2 = publisher.subscribe(integer -> System.out.println("Subscriber 2: " + integer));

        Thread.sleep(11000);
        subscribe1.dispose();
        Thread.sleep(3000);
        subscribe2.dispose();
        Thread.sleep(4000);

    }
}
