package io.test.reactorinpractice.section06.class02;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * Sinks.Many 예제
 * - replay()를 사용해서 이미 emit된 데이터 중에서 특정 개수의 최신 데이터만 전달하는 예제
 */
@Slf4j
public class SinksManyExample05 {

    public static void main(String[] args) throws InterruptedException {
        Sinks.Many<Integer> replaySinks = Sinks
                .many() // ManySpec반환
                .replay() // multicastSpec()반환. 1개 이상의 Subscriber에게 데이터를 emit.
                .all(); // 구독 시점과 상관없이 emit된 모든 데이터를 replay 함

        Flux<Integer> fluxView = replaySinks.asFlux();

        replaySinks.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        replaySinks.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);
        replaySinks.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);


        // 구독이 발생한 시점 이전의 모든 emit된 데이터를 전달 받음.
        fluxView.subscribe(data -> log.info("Subscriber1 {}", data));
        fluxView.subscribe(data -> log.info("Subscriber2 {}", data));

        Thread.sleep(1000L);
    }

}
