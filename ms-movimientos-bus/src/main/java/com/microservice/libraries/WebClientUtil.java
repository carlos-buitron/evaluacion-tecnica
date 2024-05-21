package com.microservice.libraries;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
public class WebClientUtil {

    private static final Gson GSON = new Gson();
    private static final String LOG_BASE = "{} {} Status Code:[{}] {}";

    private WebClientUtil() {
    }

    public static <R, S> R executeCall(WebClient client, HttpMethod method,
                                       String uri, Class<R> returnClass,
                                       Class<S> inputClass, S request) {
        return client
                .method(method)
                .uri(uri)
                .body(Mono.just(request), inputClass)
                .retrieve()
                .bodyToMono(returnClass)
                .block();
    }

}