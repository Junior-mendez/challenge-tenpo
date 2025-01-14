package cl.tenpo.challenge.adapters.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import cl.tenpo.challenge.application.ports.input.CallHistoryInputPort;
import cl.tenpo.challenge.utils.UtilsTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class CallHistoryControllerTest {

  @Mock private CallHistoryInputPort callHistoryInputPort;

  @InjectMocks private CallHistoryController callHistoryController;

  @Test
  void shouldReturnCallHistory() {
    int page = 0;
    int size = 1;
    given(callHistoryInputPort.getCallHistoryPaged(any(Integer.class), any(Integer.class)))
        .willReturn(UtilsTests.buildCallHistoryResponse());
    StepVerifier.create(callHistoryController.getCallHistory(page, size))
        .assertNext(
            resp -> {
              Assertions.assertThat(resp).isNotNull();
              Assertions.assertThat(resp.getCurrentPage()).isEqualTo(0);
            })
        .expectComplete()
        .verify();
  }
}
