package live.ioteatime.batchserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DemandChargeTest {

    int currentMonth = LocalDate.now().getMonthValue();

    @Test
    @DisplayName("계절 요금 메서드 테스트")
    void getSeasonalCharge() {
        Double seasonalCharge = DemandCharge.SPRING_FALL.getSeasonalCharge();

        Double expected = DemandCharge.getSeasonalCharge(currentMonth);

        assertEquals(expected, seasonalCharge);
    }

    @Test
    @DisplayName("여름 검증 테스트")
    void isSummer() {
        boolean expected = DemandCharge.isSummer(currentMonth);

        assertEquals(expected, DemandCharge.isSummer(currentMonth));
    }

    @Test
    @DisplayName("겨울 검증 테스트")
    void isWinter() {
        boolean expected = DemandCharge.isWinter(currentMonth);

        assertEquals(expected, DemandCharge.isWinter(currentMonth));
    }
}