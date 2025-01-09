package cl.tenpo.challenge.adapters.redis;

import lombok.*;

import java.io.Serializable;

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
