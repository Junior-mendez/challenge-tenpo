package cl.tenpo.challenge.adapters.clients;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import cl.tenpo.challenge.adapters.clients.config.PercentClient;
import cl.tenpo.challenge.adapters.clients.dtos.PercentClientResponse;
import cl.tenpo.challenge.adapters.redis.PercentRedisRepository;
import cl.tenpo.challenge.adapters.redis.PercentageRedis;
import cl.tenpo.challenge.domain.errors.ServicePercentageException;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class PercentAdapterTest {

  @Mock private PercentClient percentClient;

  @Mock private PercentRedisRepository percentRedisRepository;

  @InjectMocks private PercentAdapter percentAdapter;

  @Test
  void shouldReturnPercentageFailed() {
    given(percentClient.getPercent()).willReturn(Mono.empty());
    given(percentRedisRepository.get()).willReturn(Mono.empty());
    StepVerifier.create(percentAdapter.getPercent())
        .expectError(ServicePercentageException.class)
        .verify();
  }

  @Test
  void shouldReturnPercentageFromRedis() {
    given(percentClient.getPercent()).willReturn(Mono.empty());
    given(percentRedisRepository.get())
        .willReturn(
            Mono.just(
                PercentageRedis.builder()
                    .percent(10)
                    .date(LocalDateTime.now().toString())
                    .build()));
    StepVerifier.create(percentAdapter.getPercent())
        .assertNext(
            resp -> {
              Assertions.assertThat(resp).isNotNull();
            })
        .expectComplete()
        .verify();
  }

  @Test
  void shouldReturnPercentageFromRedisOutTime() {
    given(percentClient.getPercent()).willReturn(Mono.empty());
    given(percentRedisRepository.get())
        .willReturn(
            Mono.just(
                PercentageRedis.builder()
                    .percent(10)
                    .date(LocalDateTime.now().plusMinutes(80).toString())
                    .build()));
    StepVerifier.create(percentAdapter.getPercent())
        .assertNext(
            resp -> {
              Assertions.assertThat(resp).isNotNull();
            })
        .expectComplete()
        .verify();
  }

  @Test
  void shouldReturnPercentageFromClient() {
    given(percentClient.getPercent())
        .willReturn(Mono.just(PercentClientResponse.builder().percent(10).build()));
    given(percentRedisRepository.save(any(PercentageRedis.class))).willReturn(Mono.just(true));
    given(percentRedisRepository.get()).willReturn(Mono.empty());
    StepVerifier.create(percentAdapter.getPercent())
        .assertNext(
            resp -> {
              Assertions.assertThat(resp).isNotNull();
            })
        .expectComplete()
        .verify();
  }
}
