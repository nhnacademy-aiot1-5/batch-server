package live.ioteatime.batchserver.repository.impl;

import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import live.ioteatime.batchserver.domain.Consumption;
import live.ioteatime.batchserver.repository.DailyConsumptionRepository;
import live.ioteatime.batchserver.setter.ConsumptionStatementSetter;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DailyConsumptionRepositoryImpl implements DailyConsumptionRepository {

    private static final String INSERT_SQL = "insert into daily_electricity_consumption values (?, ?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Boolean saveAll(List<Consumption> consumptions) {
        BatchPreparedStatementSetter setter = new ConsumptionStatementSetter(consumptions);
        int[] result = jdbcTemplate.batchUpdate(INSERT_SQL, setter);

        return isSuccess(result);
    }

    private boolean isSuccess(int[] result) {
        return Arrays.stream(result)
                     .allMatch(i -> i == Statement.SUCCESS_NO_INFO);
    }
}
