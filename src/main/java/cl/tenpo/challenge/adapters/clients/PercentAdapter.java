package cl.tenpo.challenge.adapters.clients;

import cl.tenpo.challenge.adapters.clients.config.PercentClient;
import cl.tenpo.challenge.adapters.clients.dtos.PercentClientResponse;
import cl.tenpo.challenge.application.ports.output.PercentOutputPort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class PercentAdapter implements PercentOutputPort {

    private final PercentClient percentClient;


    @Override
    public Mono<PercentClientResponse> getPercent() {
        log.info("Call get percent");
      return  percentClient.getPercent();
    }

}
