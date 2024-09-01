package io.test.reactorinpractice.section04.class02;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

import static java.lang.Thread.sleep;

@Slf4j
public class HotSequenceExample {
    public static void main(String[] args) throws InterruptedException {
        Flux<String> concertFlux =
                Flux.fromStream(Stream.of("Singer A", "Singer B", "Singer C", "Singer D", "Singer E"))
                        .delayElements(Duration.ofSeconds(1)).share();
        // share() : coldSequence를 hotSequence로 변환. 원본 flux를 여러 Subscriber가 공유함
        // share()가 실행되면서 메인 쓰레드가 아닌 다른 스레드가 생성됨.
        concertFlux.subscribe(singer -> log.info("# Subscriber1 is watching {}'s song", singer));

        sleep(2500);

        // 구독이 발생된 이후의 emit된 데이터부터 전달 받음.
        concertFlux.subscribe(singer -> log.info("# Subscriber2 is watching {}'s song", singer));

        sleep(3000); // 코드결과를 확인하기 위해서 메인쓰레드가 먼저 종료되지않도록 sleep설정

    }
}
