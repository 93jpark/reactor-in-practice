package io.test.reactorinpractice.section06.class02;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Slf4j
public class SinkOneExample01 {
    public static void main(String[] args) {
        // emit된 데이터 중에서 단 하나의 데이터만 Subscriber에게 전달됨. 나머지 데이터는 Drop됨.
        Sinks.One<String> sinkOne = Sinks.one(); // Sinks 인터페이스의 One메소드를 통해 One객체를 얻음
        Mono<String> mono = sinkOne.asMono(); // 구독을 위해 Subscriber로 변환

        // sinkOne은 데이터를 emit하기 때문에 publisher의 역할을 함.
        sinkOne.emitValue("Hello Reactor", Sinks.EmitFailureHandler.FAIL_FAST); // emitFailureHandler를 통해 추가 동작 지정 가능

        mono.subscribe(data -> log.info("Subscriber1 {}", data));
        mono.subscribe(data -> log.info("Subscriber2 {}", data));
    }
}
