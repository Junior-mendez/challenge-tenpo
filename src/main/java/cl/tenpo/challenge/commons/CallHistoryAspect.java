package cl.tenpo.challenge.commons;

import cl.tenpo.challenge.application.ports.output.CallHistoryOutputPort;
import cl.tenpo.challenge.domain.models.CallHistory;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class CallHistoryAspect {

  private final CallHistoryOutputPort callHistoryOutputPort;

  @Around("@annotation(org.springframework.web.bind.annotation.PostMapping)")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    Class<?> targetClass = joinPoint.getTarget().getClass();
    long start = System.currentTimeMillis();
    var result = joinPoint.proceed();
    if (result instanceof Mono<?> monoResult) {
      AtomicReference<String> traceId = new AtomicReference<>("");
      return monoResult
          .doOnSuccess(
              o -> {
                callHistoryOutputPort.save(buildCallHistory(o, joinPoint, targetClass)).subscribe();
              })
          .doOnError(
              e -> {
                callHistoryOutputPort.save(buildCallHistory(e, joinPoint, targetClass)).subscribe();
              });
    }
    return result;
  }

  private String getRequestUrl(ProceedingJoinPoint joinPoint, Class<?> targetClass) {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Method method = methodSignature.getMethod();
    PostMapping methodPostMapping = method.getAnnotation(PostMapping.class);
    RequestMapping requestMapping =
        (RequestMapping) targetClass.getAnnotation(RequestMapping.class);
    return getPostUrl(requestMapping, methodPostMapping);
  }

  private String getPostUrl(RequestMapping requestMapping, PostMapping postMapping) {
    String baseUrl = getUrl(requestMapping.value());
    String endpoint = getUrl(postMapping.value());

    return baseUrl + endpoint;
  }

  private String getUrl(String[] urls) {
    if (urls.length == 0) return "";
    else return urls[0];
  }

  private CallHistory buildCallHistory(
      Object o, ProceedingJoinPoint joinPoint, Class<?> targetClass) {
    var response = "";
    if (Objects.nonNull(o)) {
      response = o.toString();
    }
    return CallHistory.builder()
        .request(joinPoint.getArgs()[0].toString())
        .date(LocalDateTime.now().toString())
        .endpoint(getRequestUrl(joinPoint, targetClass))
        .response(response)
        .build();
  }
}
