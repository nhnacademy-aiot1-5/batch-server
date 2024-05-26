package live.ioteatime.batchserver.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.sql.Statement;
import java.util.List;
import live.ioteatime.batchserver.domain.Consumption;
import live.ioteatime.batchserver.repository.impl.DailyConsumptionRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

@ExtendWith(MockitoExtension.class)
class DailyConsumptionRepositoryTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    DailyConsumptionRepositoryImpl repository;

    @Test
    void saveAll() {
        int[] statement = {
            Statement.SUCCESS_NO_INFO,
            Statement.SUCCESS_NO_INFO
        };
        given(jdbcTemplate.batchUpdate(any(), any(BatchPreparedStatementSetter.class))).willReturn(statement);
        List<Consumption> consumptions = List.of(
            new Consumption(1, "main", 1.1, 1_000L),
            new Consumption(2, "ac", 12.6, 8_320L)
        );

        Boolean result = repository.saveAll(consumptions);

        assertThat(result).isTrue();
    }

    @Test
    void saveAllFailed() {
        int[] statement = {
            Statement.SUCCESS_NO_INFO,
            Statement.EXECUTE_FAILED
        };
        given(jdbcTemplate.batchUpdate(any(), any(BatchPreparedStatementSetter.class))).willReturn(statement);
        List<Consumption> consumptions = List.of(
            new Consumption(1, "main", 1.1, 1_000L),
            new Consumption(2, "ac", 12.6, 8_320L)
        );

        Boolean result = repository.saveAll(consumptions);

        assertThat(result).isFalse();
    }
}
