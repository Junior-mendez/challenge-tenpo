package cl.tenpo.challenge.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import cl.tenpo.challenge.adapters.dtos.PercentRequest;
import cl.tenpo.challenge.adapters.dtos.PercentResponse;
import cl.tenpo.challenge.application.ports.output.PercentOutputPort;
import cl.tenpo.challenge.domain.models.Percent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class PercentServiceTest {

  @Mock private PercentOutputPort percentOutputPort;

  @InjectMocks private PercentService percentService;

  @Test
  void shouldReturnCalcPercentOkFromExternalService() {
    given(percentOutputPort.getPercent())
        .willReturn(Mono.just(Percent.builder().percent(10).build()));
    StepVerifier.create(
            percentService.calculatePercent(
                PercentRequest.builder().firstNumber(5).secondNumber(5).build()))
        .assertNext(
            resp -> {
              Assertions.assertThat(resp)
                  .isNotNull()
                  .usingRecursiveComparison()
                  .isEqualTo(PercentResponse.builder().result(11).build());
            })
        .expectComplete()
        .verify();
  }
}
