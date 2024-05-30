package live.ioteatime.batchserver.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SupplyVoltageTest {

    @Test
    @DisplayName("기본요금 테스트")
    void getGeneralCharge() {
        int demandCharge = 7170;

        int expectedCharge = SupplyVoltage.HIGH_VOLTAGE_A_OPTION_I.getGeneralCharge();

        assertEquals(expectedCharge, demandCharge);
    }
}
