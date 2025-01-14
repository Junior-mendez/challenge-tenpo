package cl.tenpo.challenge.adapters.repository.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("calls")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CallHistoryEntity {

  @Id private int id;
  private String date;
  private String endpoint;
  private String request;
  private String response;
}
