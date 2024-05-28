package live.ioteatime.batchserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * {@code DemandCharge} 클래스는 계절 별 전력량요금을 가진 도메인 클래스입니다. 봄 가을, 여름, 겨울 전력량 요금이 있습니다.
 */
@Getter
@AllArgsConstructor
public enum DemandCharge {
    SPRING_FALL(130.3),
    SUMMER(98.6),
    WINTER(142.6);

    private static final int FEBRUARY = 2;
    private static final int JUNE = 6;
    private static final int AUGUST = 8;
    private static final int NOVEMBER = 11;

    private final double demandCharge;

    /**
     * 현재 월에 해당하는 계절 별 요금을 반환하는 메소드입니다.
     *
     * @param currentMonth 현재 월(1-12)을 나타나는 파라미터입니다.
     * @return 계절별 요금을 반환합니다.
     */
    public static Double getDemandCharge(int currentMonth) {

        if (isSummer(currentMonth)) {
            return DemandCharge.SUMMER.getDemandCharge();
        }

        if (isWinter(currentMonth)) {
            return DemandCharge.WINTER.getDemandCharge();
        }

        return DemandCharge.SPRING_FALL.getDemandCharge();
    }

    public static boolean isSummer(int currentMonth) {
        return currentMonth >= JUNE && currentMonth <= AUGUST;
    }

    public static boolean isWinter(int currentMonth) {
        return currentMonth >= NOVEMBER || currentMonth <= FEBRUARY;
    }
}
