package cl.tenpo.challenge.adapters.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import cl.tenpo.challenge.adapters.dtos.PercentRequest;
import cl.tenpo.challenge.adapters.dtos.PercentResponse;
import cl.tenpo.challenge.application.ports.input.PercentInputPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class PercentControllerTest {

  @Mock private PercentInputPort percentInputPort;

  @InjectMocks private PercentController percentController;

  @Test
  void shouldReturnCalcWithPercentageOk() {
    given(this.percentInputPort.calculatePercent(any(PercentRequest.class)))
        .willReturn(Mono.just(PercentResponse.builder().result(11).build()));

    StepVerifier.create(
            this.percentController.calculatePercent(
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
