package cl.tenpo.challenge.adapters.clients.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class PercentWebClientConfig {

  @Bean("percentWebClient")
  public WebClient percentClientConfig() {
    return WebClient.builder().build();
  }
}
