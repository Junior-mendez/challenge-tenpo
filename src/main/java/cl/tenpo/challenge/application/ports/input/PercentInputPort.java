package cl.tenpo.challenge.application.ports.input;

import cl.tenpo.challenge.adapters.dtos.PercentRequest;
import cl.tenpo.challenge.adapters.dtos.PercentResponse;
import reactor.core.publisher.Mono;

public interface PercentInputPort {

  Mono<PercentResponse> calculatePercent(PercentRequest percentRequest);
}
