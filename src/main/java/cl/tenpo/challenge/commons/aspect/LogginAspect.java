package cl.tenpo.challenge.commons.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Aspect
//@Component
@Slf4j
public class LogginAspect {

    //private final CallHistoryRepository callHistoryRepository;

    @Pointcut("within(cl.tenpo.challenge.infraestructure.rest.controller.*)")
    public void loggingPoincut() {

    }

    @AfterThrowing(pointcut = "loggingPoincut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e){
        Object cause = e.getCause() == null ? "NULL" : e.getCause();
        log.error("Exception in {}.{}() with cause = '{}' and exception = '{}'",
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName()
                , cause, e);
    }

    @Around("loggingPoincut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable{
        LocalTime begin = LocalTime.now();
        Object result = null;
        Signature signature = joinPoint.getSignature();
        try{
            result = joinPoint.proceed();
            return result;
        }catch (IllegalArgumentException e){
            log.error("Ilegal argument: {} in {}#{}", objectToString(joinPoint.getArgs(),
                    "NOT ARGS"), signature.getDeclaringTypeName(), signature.getName());
            throw e;
        }finally {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
            LocalTime end = LocalTime.now();
            LocalTime start = begin;
            long duration = ChronoUnit.MILLIS.between(start, end);
            String args = objectToString(joinPoint.getArgs(), "NOT ARGS");
            String answer = objectToString(result, "NOT_RESULT");

            log.info("Execute {}#{} start={} end={} \nwith argument(s) = {} \nresult = {}",
                    signature.getDeclaringTypeName(), signature.getName(), start.format(formatter),
                    end.format(formatter), args, answer);
        }
    }

    private String objectToString(Object object, String otherwise) {
        return Objects.isNull(object)
                ? otherwise
                : object.toString();
    }
}
