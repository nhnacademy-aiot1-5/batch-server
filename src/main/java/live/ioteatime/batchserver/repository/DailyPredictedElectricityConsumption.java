package live.ioteatime.batchserver.repository;

import live.ioteatime.batchserver.domain.DailyPredictedConsumption;

import java.util.List;
import java.util.Map;

public interface DailyPredictedElectricityConsumption {

    List<DailyPredictedConsumption> findAll();

    DailyPredictedConsumption mapToBillAddedConsumption(Map<String, Object> map);

    Boolean saveAll(List<DailyPredictedConsumption> dailyPredictedConsumptions);
}
