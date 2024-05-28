package live.ioteatime.batchserver.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * {@code SupplyVoltage} 전력별 기본요금을 조회하는 클래스입니다. 전압 종류는 저압, 고압A I, 고압A II, 고압B I, 고압B II가 있습니다.
 */
@Getter
@AllArgsConstructor
public enum SupplyVoltage {
    LOW_VOLTAGE(6160),
    HIGH_VOLTAGE_A_OPTION_I(7170),
    HIGH_VOLTAGE_A_OPTION_II(8230),
    HIGH_VOLTAGE_B_OPTION_I(7170),
    HIGH_VOLTAGE_B_OPTION_II(8230);

    private final int generalCharge;
}
