package cl.tenpo.challenge.application.ports.output;

import cl.tenpo.challenge.adapters.clients.dtos.PercentClientResponse;
import reactor.core.publisher.Mono;

public interface PercentOutputPort {

    Mono<PercentClientResponse> getPercent();
}
