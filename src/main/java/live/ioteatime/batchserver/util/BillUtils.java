package live.ioteatime.batchserver.util;

import live.ioteatime.batchserver.domain.DemandCharge;
import live.ioteatime.batchserver.domain.SupplyVoltage;

import java.time.LocalDate;

/**
 * {@code BillUtils} 유틸 클래스는 전기요금 계산 시 필요한 요금들을 반환합니다.
 */
public class BillUtils {

    private static final int CONTRACTED_POWER = 10;
    private static final int CLIMATE_CHANGE_CHARGE = 9;
    private static final int FUEL_COST_ADJUSTMENT_CHARGE = 5;
    private static final double VAT = 0.1;
    private static final double ELECTRICITY_INDUSTRY_INFRASTRUCTURE_FUND = 0.037;

    private BillUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 청구요금을 반환하는 메서드입니다.
     * @param kwhUsage 전력사용량입니다.
     * @return 청구요금을 반환합니다.
     */
    public static Long getBillingCharge(Double kwhUsage) {
        long electricityBill = getElectricityBill(kwhUsage);

        return electricityBill + getVAT(electricityBill) + getElectricityIndustryInfraFund(electricityBill);
    }

    /**
     * 전기요금을 반환하는 메서드입니다.
     * @param kwhUsage 전력 사용량입니다.
     * @return 전기 요금을 반환합니다.
     */
    public static Long getElectricityBill(Double kwhUsage) {
        return getGeneralCharge()
                + getDemandCharge(kwhUsage)
                + getClimateChangeCharge(kwhUsage)
                + getFuelCostAdjustmentCharge(kwhUsage);
    }

    /**
     * 기본요금을 반환하는 메서드입니다.
     * <br>
     * 계약 전력 10kwh, 일반용 고압A I를 기준으로 계산합니다.
     * @return 사용한 일수만큼 기본요금을 반환합니다.
     */
    public static Long getGeneralCharge() {
        long generalCharge = SupplyVoltage.HIGH_VOLTAGE_A_OPTION_I.getGeneralCharge();

        return generalCharge * CONTRACTED_POWER;
    }

    /**
     * 전력요금을 반환하는 메서드입니다.
     * @param kwhUsage 전력 사용량입니다.
     * @return 사용한 전력량만큼 전력요금을 반환합니다.
     */
    public static Long getDemandCharge(Double kwhUsage) {
        int currentMonth = LocalDate.now().getMonthValue();

        double seasonalCharge = DemandCharge.getSeasonalCharge(currentMonth);

        return (long) Math.floor(seasonalCharge * kwhUsage);
    }

    /**
     * 기후변화요금을 반환하는 메서드입니다.
     * @param kwhUsage 전력 사용량압니다.
     * @return 사용한 전력만큼 기후변화요금을 반홥니다.
     */
    public static Long getClimateChangeCharge(Double kwhUsage) {
        return (long) Math.floor(kwhUsage * CLIMATE_CHANGE_CHARGE);
    }

    /**
     * 연료비조정요금을 반환하는 메서드입니다.
     * @param kwhUsage 전력 샤용량입니다.
     * @return 전력 사용량의 3.7퍼센트인 연료비조정요금을 반환합니다.
     */
    public static Long getFuelCostAdjustmentCharge(Double kwhUsage) {
        return (long) Math.floor(kwhUsage * FUEL_COST_ADJUSTMENT_CHARGE);
    }

    /**
     * 부가가치세를 반환하는 메서드입니다.
     * @param electricityBill 전기요금입니다.
     * @return 전기요금의 10%인 부가가치세를 반환합니다.
     */
    public static Long getVAT(long electricityBill) {
        return Math.round(electricityBill * VAT);
    }

    /**
     * 전력산업발전기금을 반환하는 메서드입니다.
     * <br>
     * 10원 미만은 버립니다.
     * @param electricityBill 전기요금입니다.
     * @return 전력산업발전기금을 반환합니다.
     */
    public static Long getElectricityIndustryInfraFund(long electricityBill) {
        return ((long) (electricityBill * ELECTRICITY_INDUSTRY_INFRASTRUCTURE_FUND)) / 10 * 10;
    }
}
