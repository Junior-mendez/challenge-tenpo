package cl.tenpo.challenge.adapters.clients.config;

import cl.tenpo.challenge.adapters.clients.dtos.PercentClientResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class PercentClient {

  @Value("${percent.url}")
  private String url;

  private final WebClient percentWebClient;

  @CircuitBreaker(name = "getPercentCircuitBreaker", fallbackMethod = "getPercentFallback")
  @Retry(name = "getPercentRetry")
  public Mono<PercentClientResponse> getPercent() {
    return this.percentWebClient
        .get()
        .uri(this.url)
        .retrieve()
        .toEntity(PercentClientResponse.class)
        .map(HttpEntity::getBody)
        .doOnError(error -> log.error("Error call get percent service ", error));
  }

  public Mono<?> getPercentFallback(Throwable throwable) {
    return Mono.empty();
  }
}
