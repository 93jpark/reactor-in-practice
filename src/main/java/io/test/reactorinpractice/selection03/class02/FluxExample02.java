package io.test.reactorinpractice.selection03.class02;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * Flux에서의 Operator 체인 사용 예제
 */
@Slf4j
public class FluxExample02 {
    public static void main(String[] args) {
        Flux.fromArray(new Integer[]{3, 6, 7, 9})
                .filter(num -> num > 6)
                .map(num -> num * 2)
                .subscribe(multiply -> log.info("# multiply: {}", multiply));
    }
}