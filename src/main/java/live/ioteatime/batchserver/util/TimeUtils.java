package live.ioteatime.batchserver.util;

import java.time.LocalDate;

/**
 * {@code TimeUtils} 유틸 클래스는 기준일을 가져오는 메서드를 제공합니다.
 */
public class TimeUtils {

    private TimeUtils() {
        // util class
    }

    /**
     * 기준일을 반환합니다.
     *
     * @return 기준일 {@code LocalDate} 객체
     */
    public static LocalDate getDate() {
        return LocalDate.now()
                        .minusDays(1);
    }

    /**
     * 기준일을 문자열로 반환합니다.
     *
     * @return 기준일 {@code String} 객체
     */
    public static String getDateString() {
        return getDate().toString();
    }
}
