package com.dailycodebuffer.arenxdev.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class FluxAndMonoServices {

    public Flux<String> fruitsFlux() {
        return Flux.fromIterable(List.of("Apple", "Banana", "Orange", "Grapes", "Mango"));
    }

    public Flux<String> fruitsFluxMap() {
        return Flux.fromIterable(List.of("Apple", "Banana", "Orange", "Grapes", "Mango"))
                .map(String::toUpperCase);
    }

    public Mono<String> fruitMono() {
        return Mono.just("Apple");
    }

    public Flux<String> fruitsFluxFilter(int number) {
        return Flux.fromIterable(List.of("Apple", "Banana", "Orange", "Grapes", "Mango"))
                .filter(s -> s.length() > number);
    }

    public Flux<String> fruitsFluxFilterMap(int number) {
        return Flux.fromIterable(List.of("Apple", "Banana", "Orange", "Grapes", "Mango"))
                .filter(s -> s.length() > number)
                .map(String::toUpperCase);
    }

    public Flux<String> fruitsFluxFlatMap() {
        return Flux.fromIterable(List.of("Apple", "Banana", "Orange", "Grapes", "Mango"))
                .flatMap(s -> Flux.fromArray(s.split("")));
    }

    public Flux<String> fruitsFluxFlatMapAsync() throws NoSuchAlgorithmException {
        return Flux.fromIterable(List.of("Apple", "Banana", "Orange", "Grapes", "Mango"))
                .flatMap(s -> Flux.fromArray(s.split("")))
                .delayElements(Duration.ofMillis(SecureRandom.getInstanceStrong().nextInt(1000)));
    }

    public Mono<List<String>> fruitsMonoFlatMap() {
        return Mono.just("Apple")
                .flatMap(s -> Mono.just(List.of(s.split(""))));
    }

    public Flux<String> fruitsFluxConcatMap() {
        return Flux.fromIterable(List.of("Apple", "Banana", "Orange", "Grapes", "Mango"))
                .concatMap(s -> Flux.fromArray(s.split("")));
    }

    public Flux<String> fruitsMonoFlatMapMany() {
        return Mono.just("Apple")
                .flatMapMany(s -> Flux.fromArray(s.split("")));
    }

    public Flux<String> fruitsFluxTransform(int number) {
        UnaryOperator<Flux<String>> filterData = data -> data.filter(s -> s.length() > number);
        UnaryOperator<Flux<String>> upperCaseData = data -> data.map(String::toUpperCase);

        return Flux.fromIterable(List.of("Apple", "Banana", "Orange", "Grapes", "Mango"))
                .transform(filterData)
                .transform(upperCaseData);
    }

    public Flux<String> fruitsFluxTransformDefaultIfEmpty(int number) {
        UnaryOperator<Flux<String>> filterData = data -> data.filter(s -> s.length() > number);

        return Flux.fromIterable(List.of("Apple", "Banana", "Orange", "Grapes", "Mango"))
                .transform(filterData)
                .defaultIfEmpty("DefaultValue");
    }

    public Flux<String> fruitsFluxTransformSwitchIfEmpty(int number) {
        UnaryOperator<Flux<String>> filterData = data -> data.filter(s -> s.length() > number);

        return Flux.fromIterable(List.of("Apple", "Banana", "Orange", "Grapes", "Mango"))
                .transform(filterData)
                .switchIfEmpty(Flux.just("Pineapple", "Jack Fruit"));
    }

    public Flux<String> fruitsFluxContact() {
        var fruits = Flux.just("Mango", "Orange")
                .delayElements(Duration.ofMillis(1000));
        var veggies = Flux.just("Tomato", "Lemon")
                .delayElements(Duration.ofMillis(2000));
        return Flux.concat(fruits, veggies);
    }

    public Flux<String> fruitsFluxContactWith() {
        var fruits = Flux.just("Mango", "Orange");
        var veggies = Flux.just("Tomato", "Lemon");
        return fruits.concatWith(veggies);
    }

    public Flux<String> fruitsMonoContactWith() {
        var fruit = Mono.just("Mango");
        var veggie = Mono.just("Tomato");
        return fruit.concatWith(veggie);
    }

    public Flux<String> fruitsFluxMerge() {
        var fruits = Flux.just("Mango", "Orange")
                .delayElements(Duration.ofMillis(1000));
        var veggies = Flux.just("Tomato", "Lemon")
                .delayElements(Duration.ofMillis(2000));
        return Flux.merge(fruits, veggies);
    }

    public Flux<String> fruitsFluxMergedWith() {
        var fruits = Flux.just("Mango", "Orange")
                .delayElements(Duration.ofMillis(1000));
        var veggies = Flux.just("Tomato", "Lemon")
                .delayElements(Duration.ofMillis(1500));
        return fruits.mergeWith(veggies);
    }

    /**
     * Subscribe the merged flux in parallel but emit the data sequentially
     */
    public Flux<String> fruitsFluxMergedWithSequential() {
        var fruits = Flux.just("Mango", "Orange")
                .delayElements(Duration.ofMillis(1000));
        var veggies = Flux.just("Tomato", "Lemon")
                .delayElements(Duration.ofMillis(500));
        return Flux.mergeSequential(fruits, veggies);
    }

    public Flux<String> fruitsFlaxZip() {
        var fruits = Flux.just("Mango", "Orange");
        var veggies = Flux.just("Tomato", "Lemon");
        return Flux.zip(fruits, veggies, (fruit, veggie) -> fruit + " - " + veggie);
    }

    public Flux<String> fruitsFlaxZipWith() {
        var fruits = Flux.just("Mango", "Orange");
        var veggies = Flux.just("Tomato", "Lemon");
        return fruits.zipWith(veggies, (fruit, veggie) -> fruit + " - " + veggie);
    }


    /**
     * The output will be the total of pairs elements, when the shortest flux is completed
     * they will emit a cancel signal
     */
    public Flux<String> fruitsFlaxZipTuple() {
        var fruits = Flux.just("Mango", "Orange", "Grapes").log();
        var veggies = Flux.just("Tomato", "Lemon").log();
        var moreVeggies = Flux.just("Potato", "Beans", "Carrot").log();
        return Flux.zip(fruits, veggies, moreVeggies)
                .map(tuple -> tuple.getT1() + " - " + tuple.getT2() + " - " + tuple.getT3());
    }

    public Mono<String> fruitsMonoZip() {
        var fruits = Mono.just("Mango");
        var veggies = Mono.just("Tomato");
        return fruits.zipWith(veggies, (fruit, veggie) -> fruit + " - " + veggie);
    }

    public Mono<String> zipPageableTest() {
        var fluxData = Flux.just("Mango", "Orange", "Grapes");
        var monoTotal = Mono.just(3);
        return Mono.zip(fluxData.collectList(), monoTotal, (data, total) -> data.stream().reduce((s, s2) -> s + "," + s2).orElse("") + " ---> " + total);
    }

    // doOn operators can change the flux behaviour
    public Flux<String> fruitsFluxFilterDoOn(int number) {
        return Flux.fromIterable(List.of("Apple", "Banana", "Orange", "Grapes", "Mango"))
                .log()
                .filter(s -> s.length() > number)
                .doOnNext(s -> System.out.println("doOnNext = " + s))
                .doOnSubscribe(subscription -> System.out.println("doOnSubscribe = " + subscription))
                .doOnComplete(() -> System.out.println("doOnComplete"));
    }

    // Exception Handling
    public Flux<String> fruitsFluxOnErrorReturn() {
        return Flux.just("Apple", "Banana")
                .concatWith(Flux.error(new RuntimeException("Error occurred")))
                .onErrorReturn("Unknown");
    }

    public Flux<String> fruitsFluxOnErrorContinue() {
        List<String> errors = new ArrayList<>();
        return Flux.just("Apple", "Mango", "Orange")
                .map(s -> {
                    if (s.equals("Mango")) throw new RuntimeException("Error occurred");
                    return s;
                })
                .onErrorContinue((e, o) -> errors.add(o + " - " + e.getMessage()))
                .doOnComplete(() -> System.out.println("errors = " + errors));
    }

    public Mono<Tuple2<List<String>, List<String>>> fruitsErrorsTuple() {
        List<String> errors = new ArrayList<>();
        return Flux.just("Apple", "Mango", "Orange")
                .map(s -> {
                    if (s.equals("Mango")) throw new RuntimeException("Error occurred");
                    return s;
                })
                .onErrorContinue((e, o) -> errors.add(o + " - " + e.getMessage()))
                .collectList()
                .map(list -> Tuples.of(list, errors));
    }

    public Flux<String> fruitsFluxOnErrorMap() {
        return Flux.just("Apple", "Mango", "Orange")
                .checkpoint("Error Checkpoint 1")
                .map(s -> {
                    if (s.equals("Mango")) throw new RuntimeException("Error occurred");
                    return s.toUpperCase();
                })
                .checkpoint("Error Checkpoint 2")
                .onErrorMap(e -> {
                    System.out.println("throwable = " + e);
                    return new IllegalStateException("Error occurred");
                });
    }

    public Flux<String> fruitsFluxDoOnError() {
        return Flux.just("Apple", "Mango", "Orange")
                .map(s -> {
                    if (s.equals("Mango")) throw new RuntimeException("Error occurred");
                    return s;
                })
                .log()
                .doOnError(e -> System.out.println("throwable = " + e));
    }


}