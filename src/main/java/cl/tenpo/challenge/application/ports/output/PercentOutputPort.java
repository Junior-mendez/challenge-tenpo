package cl.tenpo.challenge.application.ports.output;

import cl.tenpo.challenge.domain.models.Percent;
import reactor.core.publisher.Mono;

public interface PercentOutputPort {

  Mono<Percent> getPercent();
}
