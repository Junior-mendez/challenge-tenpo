package cl.tenpo.challenge.adapters.dtos;

import lombok.*;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse {

  private String code;
  private String title;
  private String message;
}
