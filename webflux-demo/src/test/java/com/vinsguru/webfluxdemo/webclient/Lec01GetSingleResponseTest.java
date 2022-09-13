package com.vinsguru.webfluxdemo.webclient;

import com.vinsguru.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec01GetSingleResponseTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    @Test
    public void blockTest(){

        Response response = this.webClient
                .get()
                .uri("reactive-math/square/{number}", 5)
                //if you want to send the header
//                .headers()
                // by using retrieve you will actually send req and get the resp
                .retrieve()
                .bodyToMono(Response.class) // Mono<Response>
                //using block means I ll wait until I ll get response
                .block();

        System.out.println(
                response
        );

    }

    @Test
    public void stepVerifierTest(){

        Mono<Response> responseMono = this.webClient
                .get()
                .uri("reactive-math/square/{number}", 5)
                .retrieve()
                .bodyToMono(Response.class); // Mono<Response>


        StepVerifier.create(responseMono)
                .expectNextMatches(r -> r.getOutput() == 25)
                .verifyComplete();

    }

}
