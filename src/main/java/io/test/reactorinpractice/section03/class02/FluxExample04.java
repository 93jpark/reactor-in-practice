package io.test.reactorinpractice.section03.class02;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * 여러 개의 Flux를 연결해서 하나의 Flux로 결합하는 예제
 */
@Slf4j
public class FluxExample04 {
    public static void main(String[] args) {
        Flux.concat(
                        Flux.just("Venus"),
                        Flux.just("Earth"),
                        Flux.just("Mars")
                )
                .collectList() // 한개의 리스트로 각 Flux들의 데이터를 합침
                .subscribe(planetList -> log.info("# Solar System: {}", planetList));
    }
}
