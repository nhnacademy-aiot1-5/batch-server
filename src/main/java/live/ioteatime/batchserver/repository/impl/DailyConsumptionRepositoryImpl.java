package live.ioteatime.batchserver.repository.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import live.ioteatime.batchserver.domain.Consumption;
import live.ioteatime.batchserver.repository.DailyConsumptionRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
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
        BatchPreparedStatementSetter setter = getPreparedStatementSetter(consumptions);
        int[] result = jdbcTemplate.batchUpdate(INSERT_SQL, setter);

        return isSuccess(result);
    }

    private BatchPreparedStatementSetter getPreparedStatementSetter(List<Consumption> consumptions) {
        return new BatchPreparedStatementSetter() {
            @Override
            public void setValues(@NotNull PreparedStatement ps, int i) throws SQLException {
                Consumption consumption = consumptions.get(i);
                ps.setString(1, getTodayString());
                ps.setInt(2, consumption.getChannelId());
                ps.setDouble(3, consumption.getKwh());
                ps.setLong(4, consumption.getBill());
            }

            @Override
            public int getBatchSize() {
                return consumptions.size();
            }
        };
    }

    private String getTodayString() {
        return LocalDate.now()
                        .minusDays(1)
                        .toString();
    }

    private boolean isSuccess(int[] result) {
        return Arrays.stream(result)
                     .allMatch(i -> i == Statement.SUCCESS_NO_INFO);
    }
}
