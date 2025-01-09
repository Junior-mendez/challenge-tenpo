package cl.tenpo.challenge.adapters.web;

import cl.tenpo.challenge.application.ports.input.PercentInputPort;
import cl.tenpo.challenge.adapters.dtos.PercentRequest;
import cl.tenpo.challenge.adapters.dtos.PercentResponse;
import com.sun.net.httpserver.Authenticator;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PercentController {
    private final PercentInputPort percentInputPort;
    private static final String CONTROLLER_NAME = "percent-controller";
    @PostMapping("/v1/percent/")
    //@RateLimiter(name = CONTROLLER_NAME, fallbackMethod = "fallbackMethod")
    public ResponseEntity<Mono<PercentResponse>> calculatePercent(@RequestBody PercentRequest percentRequest){
        return new ResponseEntity<>(percentInputPort.calculatePercent(percentRequest), HttpStatus.OK);
    }

    /*private Object fallbackMethod(RequestNotPermitted requestNotPermitted) {
        return Mono.just("Payment service does not permit further calls");
    }*/
}
