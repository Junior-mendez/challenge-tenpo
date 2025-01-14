package cl.tenpo.challenge.adapters.web;

import cl.tenpo.challenge.adapters.dtos.ErrorResponse;
import cl.tenpo.challenge.domain.errors.ServicePercentageException;
import cl.tenpo.challenge.domain.errors.TooManyRequestsException;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
@Slf4j
public class ErrorExceptionHandler {

  @ExceptionHandler(TooManyRequestsException.class)
  @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
  public ErrorResponse handleTooManyRequests(TooManyRequestsException ex) {
    log.error("Too Many Requests exception");
    return ErrorResponse.builder()
        .code(String.valueOf(HttpStatus.TOO_MANY_REQUESTS.value()))
        .message("Exceeded the allowed requests")
        .title(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase())
        .build();
  }

  @ExceptionHandler(ServicePercentageException.class)
  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  public ErrorResponse handleServicePercentageError(ServicePercentageException ex) {
    log.error("Service Percentage not available");
    return ErrorResponse.builder()
        .code(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()))
        .message("External service not available")
        .title(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
        .build();
  }

  @ExceptionHandler(WebExchangeBindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleConstraintViolationException(Exception ex) {
    log.error("Constraint Violation Error");
    WebExchangeBindException webExchangeBindException = (WebExchangeBindException) ex;
    List<String> resp =
        (webExchangeBindException.getBindingResult())
            .getAllErrors().stream()
                .map(
                    error -> {
                      return ((FieldError) error)
                          .getField()
                          .concat(" : ")
                          .concat(Objects.requireNonNull(((FieldError) error).getDefaultMessage()));
                    })
                .toList();
    return ErrorResponse.builder()
        .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
        .message(String.join("|", resp))
        .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
        .build();
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse handleUnknownError(Exception ex) {
    log.error("Internal Server Error");
    return ErrorResponse.builder()
        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
        .message("Unknown Error")
        .title(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
        .build();
  }
}
