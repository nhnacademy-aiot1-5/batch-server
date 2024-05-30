package live.ioteatime.batchserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * {@code Channel} 클래스는 채널과 관련된 데이터를 나타내는 도메인 클래스입니다.<br>
 * 이 클래스는 채널의 ID, 이름 속성을 포함합니다.
 */
@AllArgsConstructor
@Getter
public class Channel {

    private Integer channelId;

    private String placeName;

    private String channelName;
}
