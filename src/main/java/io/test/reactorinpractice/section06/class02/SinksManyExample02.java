package io.test.reactorinpractice.section06.class02;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * Sinks.Many 예제
 * - multicast()를 사용해서 하나 이상의 Subscriber에게 데이터를 emit하는 예제
 */
@Slf4j
public class SinksManyExample02 {

    public static void main(String[] args) {
        // 단 하나의 Subscriber에게만 데이터를 emit할 수 있음
        Sinks.Many<Integer> multicastSink = Sinks
                .many() // ManySpec반환
                .multicast() // multicastSpec()반환. 1개 이상의 Subscriber에게 데이터를 emit.
                .onBackpressureBuffer();
        Flux<Integer> fluxView = multicastSink.asFlux();

        multicastSink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        multicastSink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);

        fluxView.subscribe(data -> log.info("Subscriber1 {}", data));
        fluxView.subscribe(data -> log.info("Subscriber2 {}", data));

        multicastSink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);

    }

}
