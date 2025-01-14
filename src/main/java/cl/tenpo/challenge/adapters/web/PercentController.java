package cl.tenpo.challenge.adapters.web;

import cl.tenpo.challenge.adapters.dtos.PercentRequest;
import cl.tenpo.challenge.adapters.dtos.PercentResponse;
import cl.tenpo.challenge.application.ports.input.PercentInputPort;
import cl.tenpo.challenge.domain.errors.TooManyRequestsException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Calc amount Percentage", description = "Endpoints to calculate amount with percentage")
public class PercentController {
  private final PercentInputPort percentInputPort;
  private static final String CONTROLLER_NAME = "percent-controller";

  @Operation(
      summary = "Calculate amount with percentage",
      description = "Calculates the percentage increase of the sum of the two numbers provided")
  @ApiResponse(responseCode = "200", description = "Percentage calculation added correctly")
  @PostMapping("/v1/percent")
  @RateLimiter(name = CONTROLLER_NAME, fallbackMethod = "fallbackMethod")
  @ResponseStatus(HttpStatus.OK)
  public Mono<PercentResponse> calculatePercent(@Valid @RequestBody PercentRequest percentRequest) {
    return percentInputPort.calculatePercent(percentRequest);
  }

  private Mono<?> fallbackMethod(RequestNotPermitted requestNotPermitted) {
    return Mono.error(new TooManyRequestsException());
  }
}
