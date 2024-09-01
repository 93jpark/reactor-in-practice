package io.test.reactorinpractice.section03.class02;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;

/**
 * Mono 활용 예제
 * - worldtimeapi.org Open API를 활용해서 서울의 현재 시간을 조회함
 */
@Slf4j
public class MonoExample03 {
    public static void main(String[] args) {
        URI worldTimeUri = UriComponentsBuilder.newInstance().scheme("http")
                .host("worldtimeapi.org")
                .port(80)
                .path("/api/timezone/Asia/Seoul")
                .build()
                .encode()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // Mono는 단일 값에 대해 처리하기에 Http Request에 대한 처리를 하기에 적합함
        Mono.just(
                // Open API를 통해서 전달받은 데이터가 Mono의 데이터소스가 됨.
                restTemplate.exchange(worldTimeUri, HttpMethod.GET, new HttpEntity<String>(headers), String.class)
        )
                .map(response -> {
                    DocumentContext jsonContext = JsonPath.parse(response.getBody());
                    String dateTime = jsonContext.read("$.datatime");
                    return dateTime;
                })
                .subscribe(
                        data -> log.info("# emitted data: " + data),
                        error -> {
                            log.error(String.valueOf(error));
                        },
                        () -> log.info("# emitted onComplete signal")
                );
    }
}
