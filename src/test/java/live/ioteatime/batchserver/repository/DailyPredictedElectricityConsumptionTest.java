package live.ioteatime.batchserver.repository;

import live.ioteatime.batchserver.domain.DailyPredictedConsumption;
import live.ioteatime.batchserver.repository.impl.DailyPredictedElectricityConsumptionRepositoryImpl;
import live.ioteatime.batchserver.setter.DailyPredictedConsumptionStatementSetter;
import live.ioteatime.batchserver.util.BillUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DailyPredictedElectricityConsumptionTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    DailyPredictedElectricityConsumptionRepositoryImpl repository;

    @Test
    void findAll() {
        LocalDateTime time = LocalDateTime.parse("2024-01-01T00:00:00");
        Double kwh = 100.0;
        Integer channelId = 1;
        Integer organizationId = 1;
        Long bill = 1000L;

        Map<String, Object> map = Map.of(
            "time", time,
            "kwh", kwh,
            "channel_id", channelId,
            "organization_id", organizationId,
            "bill", bill
        );

        given(jdbcTemplate.queryForList(any())).willReturn(List.of(map));

        List<DailyPredictedConsumption> consumptions = repository.findAll();

        assertThat(consumptions).element(0)
                .hasFieldOrPropertyWithValue("time", time)
                .hasFieldOrPropertyWithValue("kwh", kwh)
                .hasFieldOrPropertyWithValue("channelId", channelId)
                .hasFieldOrPropertyWithValue("organizationId", organizationId)
                .hasFieldOrPropertyWithValue("bill", BillUtils.getDemandCharge(kwh));
    }

    @Test
    void mapToBillAddedConsumption() {
        LocalDateTime time = LocalDateTime.parse("2024-01-01T00:00:00");
        Double kwh = 100.0;
        Integer channelId = 1;
        Integer organizationId = 1;

        Map<String, Object> map = Map.of(
                "time", time,
                "kwh", kwh,
                "channel_id", channelId,
                "organization_id", organizationId,
                "bill", BillUtils.getDemandCharge(kwh)
        );

        assertThat(repository.mapToBillAddedConsumption(map))
                .hasFieldOrPropertyWithValue("time", time)
                .hasFieldOrPropertyWithValue("kwh", kwh)
                .hasFieldOrPropertyWithValue("channelId", channelId)
                .hasFieldOrPropertyWithValue("organizationId", organizationId)
                .hasFieldOrPropertyWithValue("bill", BillUtils.getDemandCharge(kwh));
    }

    @Test
    @DisplayName("일별 예측 전력소비량 저장 성공 테스트")
    void saveAllSuccess() {
        int[] statement = {1, 1, 1, 1, 1};

        given(jdbcTemplate.batchUpdate(any(), any(DailyPredictedConsumptionStatementSetter.class))).willReturn(statement);

        List<DailyPredictedConsumption> consumptions = List.of(
                new DailyPredictedConsumption(LocalDateTime.parse("2024-01-01T00:00"), 100.0, 1, 1, null),
                new DailyPredictedConsumption(LocalDateTime.parse("2024-01-02T00:00"), 200.0, 2, 1, null),
                new DailyPredictedConsumption(LocalDateTime.parse("2024-01-03T00:00"), 300.0, 3, 1, null),
                new DailyPredictedConsumption(LocalDateTime.parse("2024-01-04T00:00"), 400.0, 4, 1, null)
        );

        Boolean result = repository.saveAll(consumptions);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("일별 예측 전력소비량 저장 실패 테스트")
    void saveAllFail() {
        int[] statement = {
                2, 0, 2, 0, 1
        };

        given(jdbcTemplate.batchUpdate(any(), any(DailyPredictedConsumptionStatementSetter.class))).willReturn(statement);

        List<DailyPredictedConsumption> consumptions = List.of(
                new DailyPredictedConsumption(LocalDateTime.parse("2024-01-01T00:00"), 100.0, 1, 1, null),
                new DailyPredictedConsumption(LocalDateTime.parse("2024-01-02T00:00"), 200.0, 1, 1, null),
                new DailyPredictedConsumption(LocalDateTime.parse("2024-01-03T00:00"), 300.0, 1, 1, null),
                new DailyPredictedConsumption(LocalDateTime.parse("2024-01-04T00:00"), 400.0, 1, 1, null)
        );

        Boolean result = repository.saveAll(consumptions);

        assertThat(result).isFalse();
    }
}