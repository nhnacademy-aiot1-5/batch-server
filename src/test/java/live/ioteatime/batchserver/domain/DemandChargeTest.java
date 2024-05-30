package live.ioteatime.batchserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static live.ioteatime.batchserver.domain.DemandCharge.isSummer;
import static live.ioteatime.batchserver.domain.DemandCharge.isWinter;
import static org.junit.jupiter.api.Assertions.*;

class DemandChargeTest {

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

        assertEquals(expected, isSummer(AUGUST));
    }

    @Test
    @DisplayName("여름 검증 실패 테스트")
    void isSummerFailTest() {
        boolean expected = false;

        assertEquals(expected, isSummer(DECEMBER));
    }

    @Test
    @DisplayName("겨울 검증 성공 테스트")
    void isWinterSuccessTest() {
        boolean expected = true;

        assertEquals(expected, isWinter(DECEMBER));
    }

    @Test
    @DisplayName("겨울 검증 실패 테스트")
    void isWinterFailTest() {
        boolean expected = false;

        assertEquals(expected, isWinter(AUGUST));
    }
}
