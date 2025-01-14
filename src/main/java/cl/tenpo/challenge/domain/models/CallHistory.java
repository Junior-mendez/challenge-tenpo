package cl.tenpo.challenge.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CallHistory {

  private String date;
  private String endpoint;
  private String request;
  private String response;
}
