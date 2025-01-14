package cl.tenpo.challenge.adapters.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import cl.tenpo.challenge.adapters.repository.entities.CallHistoryEntity;
import cl.tenpo.challenge.utils.UtilsTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class CallHistoryRepositoryAdapterTest {

  @Mock private CallHistoryRepository callHistoryRepository;

  @InjectMocks private CallHistoryRepositoryAdapter callHistoryRepositoryAdapter;

  @Test
  void shouldSavedCallEntity() {
    given(callHistoryRepository.save(any(CallHistoryEntity.class)))
        .willReturn(Mono.just(UtilsTests.buildCallHistoryEntity()));
    StepVerifier.create(callHistoryRepositoryAdapter.save(UtilsTests.buildCallHistory()))
        .assertNext(resp -> Assertions.assertThat(resp).isTrue())
        .expectComplete()
        .verify();
  }

  @Test
  void shouldReturnAllCalls() {
    given(callHistoryRepository.findAllBy(any(PageRequest.class)))
        .willReturn(Flux.just(UtilsTests.buildCallHistoryEntity()));
    given(callHistoryRepository.count()).willReturn(Mono.just(1L));
    StepVerifier.create(callHistoryRepositoryAdapter.getAllCallsPaged(PageRequest.of(0, 1)))
        .assertNext(
            resp -> {
              Assertions.assertThat(resp.getNumber()).isEqualTo(0);
            })
        .expectComplete()
        .verify();
  }
}
