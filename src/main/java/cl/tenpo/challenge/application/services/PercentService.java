package cl.tenpo.challenge.application.services;

import cl.tenpo.challenge.adapters.dtos.PercentRequest;
import cl.tenpo.challenge.adapters.dtos.PercentResponse;
import cl.tenpo.challenge.application.ports.input.PercentInputPort;
import cl.tenpo.challenge.application.ports.output.PercentOutputPort;
import cl.tenpo.challenge.domain.models.Percent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class PercentService implements PercentInputPort {

  private final PercentOutputPort percentOutputPort;

  @Override
  public Mono<PercentResponse> calculatePercent(PercentRequest percentRequest) {
    return this.percentOutputPort
        .getPercent()
        .flatMap(percent -> this.calculateAmountWithPercentage(percent, percentRequest));
  }

  private Mono<PercentResponse> calculateAmountWithPercentage(
      Percent percent, PercentRequest percentRequest) {
    int sum = percentRequest.getFirstNumber() + percentRequest.getSecondNumber();
    double number = sum + ((percent.getPercent() / 100) * sum);
    return Mono.just(PercentResponse.builder().result(number).build());
  }
}
