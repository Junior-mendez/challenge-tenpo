package cl.tenpo.challenge.adapters.clients;

import cl.tenpo.challenge.adapters.clients.config.PercentClient;
import cl.tenpo.challenge.adapters.clients.dtos.PercentClientResponse;
import cl.tenpo.challenge.adapters.redis.PercentRedisRepository;
import cl.tenpo.challenge.adapters.redis.PercentageRedis;
import cl.tenpo.challenge.application.ports.output.PercentOutputPort;
import cl.tenpo.challenge.domain.errors.ServicePercentageException;
import cl.tenpo.challenge.domain.models.Percent;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class PercentAdapter implements PercentOutputPort {

  @Value("${redis.cache.percent.valid}")
  private int PERCENT_TIME_VALID;

  private final PercentClient percentClient;
  private final PercentRedisRepository percentRedisRepository;

  @Override
  public Mono<Percent> getPercent() {
    return this.percentRedisRepository
        .get()
        .flatMap(
            percentRedis -> {
              LocalDateTime dateTime = LocalDateTime.parse(percentRedis.getDate());
              return LocalDateTime.now().isAfter(dateTime.plusMinutes(PERCENT_TIME_VALID))
                  ? this.percentClient
                      .getPercent()
                      .flatMap(this::savePercentageRedis)
                      .switchIfEmpty(
                          Mono.just(Percent.builder().percent(percentRedis.getPercent()).build()))
                  : Mono.just(Percent.builder().percent(percentRedis.getPercent()).build());
            })
        .switchIfEmpty(
            this.percentClient
                .getPercent()
                .switchIfEmpty(Mono.error(new ServicePercentageException()))
                .flatMap(this::savePercentageRedis));
  }

  private Mono<Percent> savePercentageRedis(PercentClientResponse percentClientResponseMono) {
    return this.percentRedisRepository
        .save(
            PercentageRedis.builder()
                .percent(percentClientResponseMono.getPercent())
                .date(LocalDateTime.now().toString())
                .build())
        .flatMap(
            success -> {
              if (Boolean.FALSE.equals(success)) {
                Mono.error(new Exception());
              }
              return Mono.just(
                  Percent.builder().percent(percentClientResponseMono.getPercent()).build());
            });
  }
}
