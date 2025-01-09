package cl.tenpo.challenge.commons.aspect;

import cl.tenpo.challenge.adapters.dtos.PercentResponse;
import cl.tenpo.challenge.adapters.repository.CallHistoryRepository;
import cl.tenpo.challenge.adapters.repository.entities.CallHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class LogAspect {


    private final CallHistoryRepository callHistoryRepository;

        @AfterReturning(value = "@annotation(org.springframework.web.bind.annotation.PostMapping)", returning = "result")
        public void logAfterMethodReturn(JoinPoint joinPoint, Object result) {
            if (result instanceof ResponseEntity<?> responseEntity){
                if(responseEntity.getStatusCode().is2xxSuccessful()){
                    Mono<PercentResponse> monoResult = (Mono<PercentResponse>) responseEntity.getBody();
                    monoResult.map(e->{
                        callHistoryRepository.save(CallHistory.builder().date(LocalDateTime.now().toString())
                                        .request(joinPoint.getSignature().toString()).response(String.valueOf(e.getResult())).build())
                                .subscribe(data -> log.info("Data from external service: {}", e));
                    return e;});
                }

            }


        }
}

