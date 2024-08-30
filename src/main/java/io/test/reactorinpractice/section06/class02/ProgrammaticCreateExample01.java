package io.test.reactorinpractice.section06.class02;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;

/**
 * create() Operator를 사용하는 예제
 * - 일반적으로 Publisher의 데이터 생성을 단일 쓰레드에서 진행함. 멀티쓰레드에서도 가능함.
 * - 데이터 Emit은 create operator 내부에서 가능
 * - Backpressure 적용 가능
 */
@Slf4j
public class ProgrammaticCreateExample01 {
    public static void main(String[] args) {
        int tasks = 6;
        Flux    // create operator의 경우, 일반적으로 emit할 데이터를 단일 스레드에서 생성. 멀티쓰레드에서도 생성 가능
                .create((FluxSink<String> sink) -> { // FluxSink는 데이터를 동기적 또는 비동기적으로 에밋해주는 역할
                    IntStream
                            .range(1, tasks)
                            .forEach(n -> sink.next(doTask(n))); // next()를 통해서 doTask에서 리턴되는 문자열을 다운 스트림으로 emit함
                })
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(n -> log.info("# create(): {}", n))
                .publishOn(Schedulers.parallel())
                .map(result -> result + " success!")
                .doOnNext(n -> log.info("# map(): {}", n))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> log.info("# onNext: {}", data));
    }

    private static String doTask(int taskNumber) {
        // now tasking.
        // complete to task.
        return "task " + taskNumber + " result";
    }
}
