package io.test.reactorinpractice;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class HelloReactor {
    public static void main(String[] args) {
        // Publisher: Mono & Flux
        Flux<String> sequence = Flux.just("Hello", "Reactor");
        // just({emitted data}): emitter
        // sequence: emitted data

        // operator: transfer or apply operation on sequence ie. map()
        sequence.map(data -> data.toLowerCase())
                .subscribe(data -> log.info(data));
    }
}
