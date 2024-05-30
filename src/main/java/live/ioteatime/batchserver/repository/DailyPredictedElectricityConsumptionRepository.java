package live.ioteatime.batchserver.repository;

import live.ioteatime.batchserver.domain.DailyPredictedConsumption;

import java.util.List;

public interface DailyPredictedElectricityConsumptionRepository {

    List<DailyPredictedConsumption> findAll();

    Boolean saveAll(List<DailyPredictedConsumption> dailyPredictedConsumptions);
}
