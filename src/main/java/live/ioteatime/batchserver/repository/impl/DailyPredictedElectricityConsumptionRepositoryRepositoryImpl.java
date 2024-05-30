package live.ioteatime.batchserver.repository.impl;

import live.ioteatime.batchserver.domain.DailyPredictedConsumption;
import live.ioteatime.batchserver.repository.DailyPredictedElectricityConsumptionRepository;
import live.ioteatime.batchserver.setter.DailyPredictedConsumptionStatementSetter;
import live.ioteatime.batchserver.util.BillUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DailyPredictedElectricityConsumptionRepositoryRepositoryImpl implements DailyPredictedElectricityConsumptionRepository {

    private static final String FIND_ALL_SQL =
            "select time, kwh, channel_id, organization_id, bill "
                    + "from daily_predicted_electricity_consumption";

    private static final String UPDATE_SQL =
            "update daily_predicted_electricity_consumption "
                    + "set time = ?, kwh = ?, channel_id = ?, organization_id = ?, bill = ? "
                    + "where time = ? and channel_id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<DailyPredictedConsumption> findAll() {
        List<Map<String, Object>> predictedConsumptions = jdbcTemplate.queryForList(FIND_ALL_SQL);

        return predictedConsumptions.stream()
                .map(this::mapToBillAddedConsumption)
                .collect(Collectors.toUnmodifiableList());
    }

    public DailyPredictedConsumption mapToBillAddedConsumption(Map<String, Object> map) {
        LocalDateTime time = (LocalDateTime) map.get("time");
        Double kwh = (Double) map.get("kwh");
        Integer channelId = (Integer) map.get("channel_id");
        Integer organizationId = (Integer) map.get("organization_id");
        Long bill = BillUtils.getDemandCharge(kwh);

        return new DailyPredictedConsumption(time, kwh, channelId, organizationId, bill);
    }

    @Override
    public Boolean saveAll(List<DailyPredictedConsumption> dailyPredictedConsumptions) {
        BatchPreparedStatementSetter setter = new DailyPredictedConsumptionStatementSetter(dailyPredictedConsumptions);
        int[] result = jdbcTemplate.batchUpdate(UPDATE_SQL, setter);

        return isSuccess(result);
    }

    private boolean isSuccess(int[] result) {
        return Arrays.stream(result)
                .allMatch(i -> i == 1);
    }
}
