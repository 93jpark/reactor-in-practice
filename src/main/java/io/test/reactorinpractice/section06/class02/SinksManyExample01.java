package io.test.reactorinpractice.section06.class02;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * Sinks.Many 예제
 * - unicast()를 사용해서 단 하나의 Subscriber에게만 데이터를 emit하는 예제
 */
@Slf4j
public class SinksManyExample01 {
    public static void main(String[] args) {
        // 단 하나의 Subscriber에게만 데이터를 emit할 수 있음
        Sinks.Many<Integer> unicastSink = Sinks
                .many() // ManySpec반환
                .unicast() // UnicastSpec반환. 단 하나의 Subscriber에게만 데이터를 발행하는 방식
                .onBackpressureBuffer();
        Flux<Integer> fluxView = unicastSink.asFlux();

        unicastSink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        unicastSink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);

        fluxView.subscribe(data -> log.info("Subscriber1 {}", data));

        unicastSink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);
        fluxView.subscribe(data -> log.info("Subscriber2 {}", data));

    }
}
