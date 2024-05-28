package live.ioteatime.batchserver.util;

import live.ioteatime.batchserver.domain.DemandCharge;
import live.ioteatime.batchserver.domain.SupplyVoltage;

import java.time.LocalDate;

public class BillUtils {
    private static final int CLIMATE_CHANGE_CHARGE = 9;
    private static final int FUEL_COST_ADJUSTMENT_CHARGE = 5;
    private static final double VAT = 0.1;
    private static final double ELECTRICITY_INDUSTRY_INFRASTRUCTURE_FUND = 0.037;

    public static Long calculateElectricityBill(Double kwhUsage, int day) {
        long electricityBill = getGeneralCharge(day)
                + getDemandCharge(kwhUsage)
                + getClimateChangeCharge(kwhUsage)
                + getFuelCostAdjustmentCharge(kwhUsage);

        return electricityBill + getVAT(electricityBill) + getElectricityIndustryInfraFund(electricityBill);
    }

    public static Long getGeneralCharge(int day) {
        long generalCharge = SupplyVoltage.HIGH_VOLTAGE_A_OPTION_I.getGeneralCharge();

        return generalCharge * day;
    }

    public static Long getDemandCharge(Double kwhUsage) {
        int currentMonth = LocalDate.now().getMonthValue();

        double seasonalCharge = DemandCharge.getDemandCharge(currentMonth);

        return (long) Math.floor(seasonalCharge * kwhUsage);
    }

    public static Long getClimateChangeCharge(Double kwhUsage) {
        return (long) Math.floor(kwhUsage * CLIMATE_CHANGE_CHARGE);
    }

    public static Long getFuelCostAdjustmentCharge(Double kwhUsage) {
        return (long) Math.floor(kwhUsage * FUEL_COST_ADJUSTMENT_CHARGE);
    }

    public static Long getVAT(long electricityBill) {
        return Math.round(electricityBill * VAT);
    }

    public static Long getElectricityIndustryInfraFund(long electricityBill) {
        return (long) Math.floor(electricityBill * ELECTRICITY_INDUSTRY_INFRASTRUCTURE_FUND);
    }
}
