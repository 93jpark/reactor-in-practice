package io.test.reactorinpractice.section03.class02;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 2개의 Mono를 연결해서 Flux로 변환하는 예제
 */
@Slf4j
public class FluxExample03 {
    public static void main(String[] args) {
        Flux<Object> flux =
                Mono.justOrEmpty(null)
                        .concatWith(Mono.justOrEmpty("Jobs")); // Mono와 파라미터의 Mono를 합쳐주는 연산자
        flux.subscribe(data -> log.info("# result: {}", data));
    }
}
