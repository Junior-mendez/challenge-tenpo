package cl.tenpo.challenge.adapters.clients.config;

import cl.tenpo.challenge.adapters.clients.dtos.PercentClientResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class PercentClient {

    private final WebClient percentWebClient;

    @CircuitBreaker(
            name = "getPercentCircuitBreaker",
            fallbackMethod = "getPercentFallback"
    )
    @Retry(name = "getPercentRetry")
    public Mono<PercentClientResponse> getPercent(){
        log.info("call get percent client");
        return this.percentWebClient.get()
                //.uri("https://run.mocky.io/v3/9fdd46c5-3b60-4b1b-9c7e-ab360e57ae0e")
                .uri("http://localhost:8090")
                .retrieve()
                .toEntity(PercentClientResponse.class)
                .map(HttpEntity::getBody);
    }

    public Mono<?> getPercentFallback(Throwable throwable){
        log.info("Fallback get Percent");
        return Mono.empty();
    }
}
