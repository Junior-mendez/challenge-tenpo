package cl.tenpo.challenge.adapters.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class PercentResponse {

  private double result;
}
