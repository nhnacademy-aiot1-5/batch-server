package live.ioteatime.batchserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * {@code Energy} 클래스는 전력 사용량과 관련된 데이터를 나타내는 도메인 클래스입니다.<br>
 * 이 클래스는 타입, 전력 사용량 속성을 포함합니다.
 */
@AllArgsConstructor
@Getter
public class Energy {

    private String place;

    private String type;

    private Double kwh;
}
