package live.ioteatime.batchserver.setter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import live.ioteatime.batchserver.domain.Consumption;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

public class ConsumptionStatementSetter implements BatchPreparedStatementSetter {

    private final List<Consumption> consumptions;

    public ConsumptionStatementSetter(List<Consumption> consumptions) {
        this.consumptions = consumptions;
    }

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

    private String getTodayString() {
        return LocalDate.now()
                        .minusDays(1)
                        .toString();
    }
}
