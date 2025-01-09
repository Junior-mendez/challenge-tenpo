package cl.tenpo.challenge.commons.aspect;

import cl.tenpo.challenge.adapters.repository.entities.CallHistory;
import cl.tenpo.challenge.adapters.repository.CallHistoryRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.concurrent.Flow;

@Aspect
public class PostRequestLogger {

    @Autowired
    private CallHistoryRepository callHistoryRepository;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postAction() {
    }

    @Around("postAction()")
    public Publisher<?> logAction(ProceedingJoinPoint joinPoint) throws Throwable {
        Class clazz = joinPoint.getTarget().getClass();
        Logger logger = LoggerFactory.getLogger(clazz);

        String url = getRequestUrl(joinPoint, clazz);
        String payload = getPayload(joinPoint);



        //callHistoryRepository.save(callHistory)
          //              .subscribe();
        //logger.info("POST " + url + " Payload " + payload + "Response " + result);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Class<?> returnType = methodSignature.getReturnType();
        if (Mono.class.isAssignableFrom(returnType)) {
            Mono<?> result = (Mono<?>) joinPoint.proceed();
            logger.info("POST " + url + " Payload " + payload + "Response " + result);
            return result.map(e->{
                CallHistory callHistory = CallHistory.builder()
                        .request(payload)
                        .response(result.toString()).build();
                callHistoryRepository.save(callHistory);
                return e; });
        }
        return (Publisher<?>) joinPoint.proceed();
    }
    @AfterReturning(pointcut = "execution(cl.tenpo.challenge.infraestructure.rest.controller.*)", returning = "result")
    public void logAfterMethodReturn(Object result) {
        if (Mono.class.isAssignableFrom(result.getClass())) {
            Mono<?> resultMono = (Mono<?>) result;

            resultMono.map(e->{
                CallHistory callHistory = CallHistory.builder()
                        .request(result.toString())
                        .response(result.toString()).build();
                callHistoryRepository.save(callHistory).subscribe();
                return e; });
        }

    }




    private String getRequestUrl(ProceedingJoinPoint joinPoint, Class clazz) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        PostMapping methodPostMapping = method.getAnnotation(PostMapping.class);
        RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
        return getPostUrl(requestMapping, methodPostMapping);
    }

    private String getPayload(ProceedingJoinPoint joinPoint) {
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            String parameterName = signature.getParameterNames()[i];
            builder.append(parameterName);
            builder.append(": ");
            builder.append(joinPoint.getArgs()[i].toString());
            builder.append(", ");
        }
        return builder.toString();
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
}