package cl.tenpo.challenge.application.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import cl.tenpo.challenge.application.ports.output.CallHistoryOutputPort;
import cl.tenpo.challenge.utils.UtilsTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class CallHistoryServiceTest {

  @Mock private CallHistoryOutputPort callHistoryOutputPort;

  @InjectMocks private CallHistoryService callHistoryService;

  @Test
  void shouldCallHistoryOk() {

    given(callHistoryOutputPort.getAllCallsPaged(any(PageRequest.class)))
        .willReturn(UtilsTests.buildPageCallHistoryEntity());

    StepVerifier.create(callHistoryService.getCallHistoryPaged(0, 1))
        .assertNext(
            resp -> {
              Assertions.assertThat(resp).isNotNull();
              resp.getCallHistories()
                  .forEach(
                      resp1 -> {
                        Assertions.assertThat(resp1.getEndpoint()).isEqualTo("/api/v1/percent");
                      });
            })
        .expectComplete()
        .verify();
  }
}
