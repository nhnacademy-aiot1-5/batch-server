package live.ioteatime.batchserver.setter;

import live.ioteatime.batchserver.domain.DailyPredictedConsumption;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class DailyPredictedConsumptionStatementSetter implements BatchPreparedStatementSetter {

    private final List<DailyPredictedConsumption> dailyPredictedConsumptions;

    @Override
    public void setValues(PreparedStatement ps, int i) throws SQLException {
        DailyPredictedConsumption dailyPredictedConsumption = dailyPredictedConsumptions.get(i);
        ps.setString(1, String.valueOf(dailyPredictedConsumption.getTime()));
        ps.setDouble(2, dailyPredictedConsumption.getKwh());
        ps.setInt(3, dailyPredictedConsumption.getChannelId());
        ps.setInt(4, dailyPredictedConsumption.getOrganizationId());
        ps.setLong(5, dailyPredictedConsumption.getBill());
        ps.setString(6, String.valueOf(dailyPredictedConsumption.getTime()));
        ps.setInt(7, dailyPredictedConsumption.getChannelId());
    }

    @Override
    public int getBatchSize() {
        return dailyPredictedConsumptions.size();
    }
}
