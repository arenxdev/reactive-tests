package com.dailycodebuffer.arenxdev.services;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;

class FluxAndMonoServicesTest {

    FluxAndMonoServices fluxAndMonoServices = new FluxAndMonoServices();

    @Test
    void fruitsFlux() {
        var fruitsFlux = fluxAndMonoServices.fruitsFlux();

        StepVerifier.create(fruitsFlux.log())
                .expectNext("Apple", "Banana", "Orange", "Grapes", "Mango")
                .verifyComplete();

    }

    @Test
    void fruitMono() {
        var fruitsMono = fluxAndMonoServices.fruitMono();
        StepVerifier.create(fruitsMono.log())
                .expectNext("Apple")
                .verifyComplete();
    }

    @Test
    void fruitsFluxMap() {
        var fruitsFluxMap = fluxAndMonoServices.fruitsFluxMap();
        StepVerifier.create(fruitsFluxMap.log())
                .expectNext("APPLE", "BANANA", "ORANGE", "GRAPES", "MANGO")
                .verifyComplete();
    }

    @Test
    void fruitsFluxFilter() {
        var fruitsFluxFilter = fluxAndMonoServices.fruitsFluxFilter(5);
        StepVerifier.create(fruitsFluxFilter.log())
                .expectNext("Banana", "Orange", "Grapes")
                .verifyComplete();
    }


    @Test
    void fruitsFluxFilterMap() {
        var fruitsFluxFilterMap = fluxAndMonoServices.fruitsFluxFilterMap(5);
        StepVerifier.create(fruitsFluxFilterMap.log())
                .expectNext("BANANA", "ORANGE", "GRAPES")
                .verifyComplete();
    }

    @Test
    void fruitsFluxFlatMap() {
        var fruitsFluxFlatMap = fluxAndMonoServices.fruitsFluxFlatMap();
        StepVerifier.create(fruitsFluxFlatMap.log())
                .expectNextCount(28)
                .verifyComplete();
    }

    @Test
    void fruitsFluxFlatMapAsync() throws NoSuchAlgorithmException {
        var fruitsFluxFlatMapAsync = fluxAndMonoServices.fruitsFluxFlatMapAsync();
        StepVerifier.create(fruitsFluxFlatMapAsync.log())
                .expectNextCount(28)
                .verifyComplete();
    }

    @Test
    void fruitsMonoFlatMap() {
        var fruitsMonoFlatMap = fluxAndMonoServices.fruitsMonoFlatMap();
        StepVerifier.create(fruitsMonoFlatMap.log())
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void fruitsFluxConcatMap() {
        var fruitsFluxConcatMap = fluxAndMonoServices.fruitsFluxConcatMap();
        StepVerifier.create(fruitsFluxConcatMap.log())
                .expectNextCount(28)
                .verifyComplete();
    }

    @Test
    void fruitsMonoFlatMapMany() {
        var fruitsMonoFlatMapMany = fluxAndMonoServices.fruitsMonoFlatMapMany();
        StepVerifier.create(fruitsMonoFlatMapMany.log())
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    void fruitsFluxTransform() {
        var fruitsFluxTransform = fluxAndMonoServices.fruitsFluxTransform(5);
        StepVerifier.create(fruitsFluxTransform.log())
                .expectNext("BANANA", "ORANGE", "GRAPES")
                .verifyComplete();
    }

    @Test
    void fruitsFluxTransformDefaultIfEmpty() {
        var fruitsFluxTransformDefaultIfEmpty = fluxAndMonoServices.fruitsFluxTransformDefaultIfEmpty(10);
        StepVerifier.create(fruitsFluxTransformDefaultIfEmpty.log())
                .expectNext("DefaultValue")
                .verifyComplete();
    }

    @Test
    void fruitsFluxTransformSwitchIfEmpty() {
        var fruitsFluxTransformDefaultIfEmpty = fluxAndMonoServices.fruitsFluxTransformSwitchIfEmpty(10);
        StepVerifier.create(fruitsFluxTransformDefaultIfEmpty.log())
                .expectNextCount(2)
                .verifyComplete();
    }


    @Test
    void fruitsFluxContact() {
        var fruitsAndVeggiesFlux = fluxAndMonoServices.fruitsFluxContact();
        StepVerifier.create(fruitsAndVeggiesFlux.log())
                .expectNext("Mango", "Orange", "Tomato", "Lemon")
                .verifyComplete();
    }

    @Test
    void fruitsFluxContactWith() {
        var fruitsAndVeggiesFlux = fluxAndMonoServices.fruitsFluxContactWith();
        StepVerifier.create(fruitsAndVeggiesFlux.log())
                .expectNext("Mango", "Orange", "Tomato", "Lemon")
                .verifyComplete();
    }


    @Test
    void fruitsMonoContactWith() {
        var fruitsAndVeggiesFlux = fluxAndMonoServices.fruitsMonoContactWith();
        StepVerifier.create(fruitsAndVeggiesFlux.log())
                .expectNext("Mango", "Tomato")
                .verifyComplete();
    }


    @Test
    void fruitsFluxMerge() {
        var fruitsAndVeggiesFlux = fluxAndMonoServices.fruitsFluxMerge();
        StepVerifier.create(fruitsAndVeggiesFlux.log())
                .expectNext("Mango", "Tomato", "Orange", "Lemon")
                .verifyComplete();
    }

    @Test
    void fruitsFluxMergedWith() {
        var fruitsAndVeggiesFlux = fluxAndMonoServices.fruitsFluxMergedWith();
        StepVerifier.create(fruitsAndVeggiesFlux.log())
                .expectNext("Mango", "Tomato", "Orange", "Lemon")
                .verifyComplete();
    }

    @Test
    void fruitsFluxMergedWithSequential() {
        var fruitsAndVeggiesFlux = fluxAndMonoServices.fruitsFluxMergedWithSequential();
        StepVerifier.create(fruitsAndVeggiesFlux.log())
                .expectNext("Mango", "Orange", "Tomato", "Lemon")
                .verifyComplete();
    }

    @Test
    void fruitsFlaxZip() {
        var fruitsAndVeggiesFlux = fluxAndMonoServices.fruitsFlaxZip();
        StepVerifier.create(fruitsAndVeggiesFlux.log())
                .expectNext("Mango - Tomato", "Orange - Lemon")
                .verifyComplete();
    }

    @Test
    void fruitsFlaxZipWith() {
        var fruitsAndVeggiesFlux = fluxAndMonoServices.fruitsFlaxZipWith();
        StepVerifier.create(fruitsAndVeggiesFlux.log())
                .expectNext("Mango - Tomato", "Orange - Lemon")
                .verifyComplete();
    }

    @Test
    void fruitsFlaxZipTuple() {
        var fruitsAndVeggiesFlux = fluxAndMonoServices.fruitsFlaxZipTuple();
        StepVerifier.create(fruitsAndVeggiesFlux.log("zipped"))
                .expectNext("Mango - Tomato - Potato", "Orange - Lemon - Beans")
                .verifyComplete();
    }

    @Test
    void fruitsMonoZip() {
        var fruitsAndVeggiesMono = fluxAndMonoServices.fruitsMonoZip();
        StepVerifier.create(fruitsAndVeggiesMono.log())
                .expectNext("Mango - Tomato")
                .verifyComplete();
    }

    @Test
    void zipPageableTest() {
        var zipPageable = fluxAndMonoServices.zipPageableTest();
        StepVerifier.create(zipPageable.log())
                .expectNext("Mango,Orange,Grapes ---> 3")
                .verifyComplete();
    }


    @Test
    void fruitsFluxFilterDoOn() {
        StepVerifier.create(fluxAndMonoServices.fruitsFluxFilterDoOn(5))
                .expectNext("Banana", "Orange", "Grapes")
                .verifyComplete();
    }

    @Test
    void fruitsFluxOnErrorReturn() {
        StepVerifier.create(fluxAndMonoServices.fruitsFluxOnErrorReturn().log())
                .expectNext("Apple", "Banana", "Unknown")
                .verifyComplete();
    }

    @Test
    void fruitsFluxOnErrorContinue() {
        StepVerifier.create(fluxAndMonoServices.fruitsFluxOnErrorContinue().log())
                .expectNext("Apple", "Orange")
                .verifyComplete();
    }

    @Test
    void fruitsErrorsTuple() {
        StepVerifier.create(fluxAndMonoServices.fruitsErrorsTuple().log())
                .assertNext(tuple -> assertThat(tuple).hasSize(2))
                .verifyComplete();
    }

    @Test
    void fruitsFluxOnErrorMap() {
        StepVerifier.create(fluxAndMonoServices.fruitsFluxOnErrorMap().log())
                .expectNext("Apple")
                .expectError(IllegalStateException.class)
                .verify();
    }

    @Test
    void fruitsFluxDoOnError() {
        StepVerifier.create(fluxAndMonoServices.fruitsFluxDoOnError().log())
                .expectNext("Apple")
                .expectError(RuntimeException.class)
                .verify();
    }
}