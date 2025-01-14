package cl.tenpo.challenge.adapters.web;

import static org.junit.jupiter.api.Assertions.*;

import cl.tenpo.challenge.domain.errors.ServicePercentageException;
import cl.tenpo.challenge.domain.errors.TooManyRequestsException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ErrorExceptionHandlerTest {

  @InjectMocks private ErrorExceptionHandler errorExceptionHandler;

  @Test
  void shouldHandleTooManyRequests() {
    Assertions.assertThat(
            errorExceptionHandler.handleTooManyRequests(new TooManyRequestsException()))
        .isNotNull()
        .matches(
            errorResponse -> errorResponse.getMessage().contains("Exceeded the allowed requests"));
  }

  @Test
  void shouldHandleServicePercentageError() {
    Assertions.assertThat(
            errorExceptionHandler.handleServicePercentageError(new ServicePercentageException()))
        .isNotNull()
        .matches(
            errorResponse -> errorResponse.getMessage().contains("External service not available"));
  }

  @Test
  void shouldHandleUnknownError() {
    Assertions.assertThat(errorExceptionHandler.handleUnknownError(new RuntimeException()))
        .isNotNull()
        .matches(errorResponse -> errorResponse.getMessage().contains("Unknown Error"));
  }
}
