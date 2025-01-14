package cl.tenpo.challenge.adapters.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.*;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class RedisConfig {

  private final SpringRedisConfig springRedisConfig;

  @Bean
  @Primary
  public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {

    return new LettuceConnectionFactory(this.springRedisConfig.redisStandaloneConfiguration());
  }

  @Bean
  public ReactiveValueOperations<String, PercentageRedis> hashOperations(
      ReactiveRedisConnectionFactory factory) {
    Jackson2JsonRedisSerializer<PercentageRedis> serializer =
        new Jackson2JsonRedisSerializer<>(PercentageRedis.class);
    RedisSerializationContext<String, PercentageRedis> context =
        RedisSerializationContext.<String, PercentageRedis>newSerializationContext(
                new StringRedisSerializer())
            .key(new StringRedisSerializer())
            .value(serializer)
            .hashKey(new StringRedisSerializer())
            .hashValue(serializer)
            .build();
    ReactiveRedisTemplate<String, PercentageRedis> template =
        new ReactiveRedisTemplate<>(factory, context);
    return template.opsForValue();
  }
}
