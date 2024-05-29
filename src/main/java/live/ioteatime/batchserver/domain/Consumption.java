package live.ioteatime.batchserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code Consumption} 클래스는 전력 사용량과 관련된 데이터를 나타내는 도메인 클래스입니다.<br>
 * 이 클래스는 채널의 ID, 이름, 전력 사용량, 전기 요금 속성을 포함합니다.
 */
@AllArgsConstructor
@Getter
public class Consumption {

    private Integer channelId;

    private String type;

    @Setter
    private Double kwh;

    @Setter
    private Long bill;
}
