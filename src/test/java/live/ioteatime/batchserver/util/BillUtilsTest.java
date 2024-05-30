package live.ioteatime.batchserver.util;

import live.ioteatime.batchserver.domain.DemandCharge;
import live.ioteatime.batchserver.domain.SupplyVoltage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BillUtilsTest {
    Double kwhUsage = 100.0;

    @Test
    @DisplayName("생성자 생성 시 예외 발생 테스트")
    void privateConstructorExceptionTest() throws NoSuchMethodException {
        Constructor<BillUtils> constructor = BillUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        try {
            constructor.newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            assertThrows(IllegalStateException.class, () -> { throw e.getCause(); });
        }
    }

    @Test
    @DisplayName("전기요금 테스트")
    void getBillingCharge() {
        long electricityBill = BillUtils.getGeneralCharge()
                + BillUtils.getDemandCharge(kwhUsage)
                + BillUtils.getClimateChangeCharge(kwhUsage)
                + BillUtils.getFuelCostAdjustmentCharge(kwhUsage);

        Long expectedBill = electricityBill + BillUtils.getVAT(electricityBill)
                + BillUtils.getElectricityIndustryInfraFund(electricityBill);

        assertEquals(expectedBill, BillUtils.getBillingCharge(kwhUsage));
    }

    @Test
    @DisplayName("기본요금 테스트")
    void getGeneralCharge() {
        long generalCharge = SupplyVoltage.HIGH_VOLTAGE_A_OPTION_I.getGeneralCharge();

        int contractedPower = 10;

        Long expectedCharge = generalCharge * contractedPower;

        assertEquals(expectedCharge, BillUtils.getGeneralCharge());
    }

    @Test
    @DisplayName("전력요금 테스트")
    void getDemandCharge() {
        int currentMonth = LocalDate.now().getMonthValue();

        Double seasonalCharge = DemandCharge.getSeasonalCharge(currentMonth);

        Long expectedCharge = (long) Math.floor(seasonalCharge * kwhUsage);

        assertEquals(expectedCharge, BillUtils.getDemandCharge(kwhUsage));
    }

    @Test
    @DisplayName("기후변화요금 테스트")
    void getClimateChangeCharge() {
        int CLIMATE_CHANGE_CHARGE = 9;

        Long expectedCharge = (long) Math.floor(kwhUsage * CLIMATE_CHANGE_CHARGE);

        assertEquals(expectedCharge, BillUtils.getClimateChangeCharge(kwhUsage));
    }

    @Test
    @DisplayName("연료비조정요금 테스트")
    void getFuelCostAdjustmentCharge() {
        int FUEL_COST_ADJUSTMENT_CHARGE = 5;

        Long expectedCharge = Math.round(kwhUsage * FUEL_COST_ADJUSTMENT_CHARGE);

        assertEquals(expectedCharge, BillUtils.getFuelCostAdjustmentCharge(kwhUsage));
    }

@Test
    @DisplayName("부가가치세 테스트")
    void getVAT() {
        long electricityBill = 10000L;

        double VAT = 0.1;

        Long expectedVAT = Math.round(electricityBill * VAT);

        assertEquals(expectedVAT, BillUtils.getVAT(electricityBill));
    }

    @Test
    @DisplayName("전력산업기반기금 테스트")
    void getElectricityIndustryInfraFund() {
        long electricityBill = 10000L;

        double ELECTRICITY_INFRA_FUND_CHARGE = 0.037;

        Long expectedFund = (long) Math.floor(electricityBill * ELECTRICITY_INFRA_FUND_CHARGE);

        assertEquals(expectedFund, BillUtils.getElectricityIndustryInfraFund(electricityBill));
    }
}
