package live.ioteatime.batchserver.setter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.PreparedStatement;
import java.util.List;
import live.ioteatime.batchserver.domain.Consumption;
import live.ioteatime.batchserver.util.TimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsumptionStatementSetterTest {

    ConsumptionStatementSetter setter;

    @BeforeEach
    void setUp() {
        List<Consumption> consumptions = List.of(
            new Consumption(1, "main", 100.0, 1_000L)
        );
        setter = new ConsumptionStatementSetter(consumptions);
    }

    @Test
    void setValues() throws Exception {
        PreparedStatement ps = mock(PreparedStatement.class);

        setter.setValues(ps, 0);

        verify(ps, times(1)).setString(1, TimeUtils.getDateString());
        verify(ps, times(1)).setInt(2, 1);
        verify(ps, times(1)).setDouble(3, 100.0);
        verify(ps, times(1)).setLong(4, 1_000L);
    }

    @Test
    void getBatchSize() {
        int batchSize = setter.getBatchSize();

        assertThat(batchSize).isEqualTo(1);
    }
}
