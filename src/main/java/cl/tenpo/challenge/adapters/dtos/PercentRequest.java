package cl.tenpo.challenge.adapters.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PercentRequest {

  @Positive(message = "Wrong number")
  private int firstNumber;

  @Positive(message = "Wrong number")
  private int secondNumber;
}
