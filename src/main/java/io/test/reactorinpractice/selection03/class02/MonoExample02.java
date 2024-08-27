package io.test.reactorinpractice.selection03.class02;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 *  Mono 기본 개념 예제
 *  - 원본 데이터의 emit 없이 onComplete signal만 emit함
 */
@Slf4j
public class MonoExample02 {
    public static void main(String[] args) {
        Mono.empty() // 0개 또는 1개의 데이터를 emit함
                .subscribe(
                        data -> log.info("# emitted data: {}", data), // 데이터를 받으면 해당 처리
                        error -> {}, // 데이터 처리 중 에러 발생 시 처리구문
                        () -> log.info("# emitted onComplete signal") // 데이터 처리 후 onComplete signal 처리
                );
    }
}
