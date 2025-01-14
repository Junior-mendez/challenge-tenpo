package cl.tenpo.challenge.adapters.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
@RequiredArgsConstructor
public class PercentRedisRepository {

  @Value("${redis.cache.key}")
  private String PERCENT_KEY;

  private final ReactiveValueOperations<String, PercentageRedis> reactiveValueOperations;

  public Mono<Boolean> save(PercentageRedis percentRedis) {
    return reactiveValueOperations.set(PERCENT_KEY, percentRedis);
  }

  public Mono<PercentageRedis> get() {
    return reactiveValueOperations.get(PERCENT_KEY);
  }
}
