package live.ioteatime.batchserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class DailyPredictedConsumption {

    private LocalDateTime time;

    private Double kwh;

    @Setter
    private Integer channelId;

    @Setter
    private Integer organizationId;

    private Long bill;
}