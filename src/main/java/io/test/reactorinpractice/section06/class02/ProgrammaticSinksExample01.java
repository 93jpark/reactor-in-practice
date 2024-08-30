package io.test.reactorinpractice.section06.class02;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;

/**
 *
 */
@Slf4j
public class ProgrammaticSinksExample01 {
    public static void main(String[] args) {
        int tasks = 6;

        // Sinks의 경우, 데이터를 emit할 때 Sink 객체를 생성함.
        Sinks.Many<String> unicastSink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<String> fluxView = unicastSink.asFlux(); // asFlux()를 통해서 Sinks를 Flux로 변환
        IntStream
                .range(1, tasks)
                        .forEach(n -> {
                            try {
                                new Thread(() -> {
                                    // emitNext()를 통해서 데이터를 emit
                                    unicastSink.emitNext(doTask(n), Sinks.EmitFailureHandler.FAIL_FAST);
                                    log.info("# emitted: {}", n);
                                }).start();
                                Thread.sleep(100L);
                            } catch (InterruptedException e) {}
                        });

        // Thread를 추가로 생성하는 부분
        fluxView
                .publishOn(Schedulers.parallel())
                .map(result -> result + " success!")
                .doOnNext(n -> log.info("# map: {}", n))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> log.info("# onNext: {}", data)); // 위의 Sinks에서 변환된 Flux를 구독

    }

    private static String doTask(int taskNumber) {
        // now tasking.
        // complete to task.
        return "task " + taskNumber + " result";
    }
}
