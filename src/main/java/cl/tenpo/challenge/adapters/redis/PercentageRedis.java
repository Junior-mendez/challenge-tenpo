package cl.tenpo.challenge.adapters.redis;

import java.io.Serializable;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PercentageRedis implements Serializable {

  private double percent;

  private String date;
}
