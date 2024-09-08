package io.test.reactorinpractice.section06.class02;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * Sinks.Many 예제
 * - replay()를 사용해서 이미 emit된 데이터 중에서 특정 개수의 최신 데이터만 전달하는 예제
 */
@Slf4j
public class SinksManyExample04 {

    public static void main(String[] args) {
        // 구독 이후, emit된 데이터 중에서 최신 데이터 2개만 replay 함.
        Sinks.Many<Integer> replaySinks = Sinks
                .many() // ManySpec반환
                .replay() // multicastSpec()반환. 1개 이상의 Subscriber에게 데이터를 emit.
                .limit(2);

        Flux<Integer> fluxView = replaySinks.asFlux();

        replaySinks.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        replaySinks.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);
        replaySinks.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);

        // subscriber가 구독을 하는 시점에 가장 최근의 emit된 데이터를 전달받음
        fluxView.subscribe(data -> log.info("Subscriber1 {}", data)); // 가장 최근 2개인 2,3이 출력

        replaySinks.emitNext(4, Sinks.EmitFailureHandler.FAIL_FAST); // 첫번째 Subscriber에게도 4가 전달됨

        fluxView.subscribe(data -> log.info("Subscriber2 {}", data)); // 해당 구독시점에서는 3,4만 출력됨

    }

}
