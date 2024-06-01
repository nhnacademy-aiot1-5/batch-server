package live.ioteatime.batchserver.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * {@code Period} 클래스는 쿼리 종류를 나타내는 열거형 클래스입니다.
 */
@Getter
@RequiredArgsConstructor
public enum Period {
    DAILY("sum", "difference()", "T15:00:00Z", "T15:00:00Z", "24h"),
    MONTHLY("this_month", "yield(name: \"last\")", "T14:58:00Z", "T14:59:00Z", "1m");

    private final String description;

    private final String query;

    private final String startTime;

    private final String stopTime;

    private final String term;
}
