package cl.tenpo.challenge.adapters.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@Data
public class PercentRequest {

    private int firstNumber;
    private int secondNumber;
}
