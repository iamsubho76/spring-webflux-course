package com.vinsguru.webfluxdemo.service;

import com.vinsguru.webfluxdemo.dto.MultiplyRequestDto;
import com.vinsguru.webfluxdemo.dto.Response;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ReactiveMathService {

    public Mono<Response> findSquare(int input){
        return Mono.fromSupplier(() -> input * input)
                    .map(Response::new);
    }

    public Flux<Response> multiplicationTable(int input){
        return Flux.range(1, 10)
                //delayElements is a non-blocking sleep and doOnNext is a blocking sleep
                    .delayElements(Duration.ofSeconds(1))
                    //.doOnNext(i -> SleepUtil.sleepSeconds(1))
                    .doOnNext(i -> System.out.println("reactive-math-service processing : " + i))
                    .map(i -> new Response(i * input));
        //below one is not recommended as because if you stop the subscriber the processing in server won't stop
//        List<Response> collect = IntStream.rangeClosed(1, 10)
//                .peek(i -> SleepUtil.sleepSeconds(1))
//                .peek(i -> System.out.println("math-service processing : " + i))
//                .mapToObj(i -> new Response(i * input))
//                .collect(Collectors.toList());
//
//        return Flux.fromIterable(collect);
    }

    public Mono<Response> multiply(Mono<MultiplyRequestDto> dtoMono){
        return dtoMono
                    .map(dto -> dto.getFirst() * dto.getSecond())
                    .map(Response::new);
    }

}
