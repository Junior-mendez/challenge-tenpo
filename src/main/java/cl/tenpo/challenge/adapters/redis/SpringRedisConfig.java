package cl.tenpo.challenge.adapters.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

@ConfigurationProperties(prefix = "spring.data.redis")
public record SpringRedisConfig (String username, String password, int port, String host){
    public RedisStandaloneConfiguration redisStandaloneConfiguration(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(this.host, this.port);
        redisStandaloneConfiguration.setUsername(this.username);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(this.password));
        return redisStandaloneConfiguration;
    }
}
