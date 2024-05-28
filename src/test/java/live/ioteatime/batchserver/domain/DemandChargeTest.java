package live.ioteatime.batchserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DemandChargeTest {

    int currentMonth = LocalDate.now().getMonthValue();
    int AUGUST = 8;
    int DECEMBER = 12;

    @Test
    @DisplayName("계절 요금 메서드 테스트")
    void getSeasonalChargeTest() {
        Double seasonalCharge = DemandCharge.getSeasonalCharge(AUGUST);

        Double expected = DemandCharge.SUMMER.getSeasonalCharge();

        assertEquals(expected, seasonalCharge);
    }

    @Test
    @DisplayName("여름 검증 성공 테스트")
    void isSummerSuccessTest() {
        boolean expected = true;

        assertEquals(expected, DemandCharge.isSummer(AUGUST));
    }

    @Test
    @DisplayName("여름 검증 실패 테스트")
    void isSummerFailTest() {
        boolean expected = false;

        assertEquals(expected, DemandCharge.isSummer(DECEMBER));
    }

    @Test
    @DisplayName("겨울 검증 성공 테스트")
    void isWinterSuccessTest() {
        boolean expected = true;

        assertEquals(expected, DemandCharge.isWinter(DECEMBER));
    }

    @Test
    @DisplayName("겨울 검증 실패 테스트")
    void isWinterFailTest() {
        boolean expected = false;

        assertEquals(expected, DemandCharge.isWinter(AUGUST));
    }
}
