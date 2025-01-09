package cl.tenpo.challenge.adapters.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
@RequiredArgsConstructor
public class PercentRedisRepository {

    private final ReactiveValueOperations<String, PercentageRedis> reactiveValueOperations;

    public Mono<Boolean> save (PercentageRedis percentRedis){

      log.info("Saving in redis percent: {}",percentRedis);
      return reactiveValueOperations
              .set("percent", percentRedis);
    }
    public Mono<PercentageRedis> get (){
        log.info("Getting percent from redis");
        return reactiveValueOperations
                .get("percent");
    }
}
