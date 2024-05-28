package live.ioteatime.batchserver.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import java.util.LinkedHashMap;
import java.util.List;
import live.ioteatime.batchserver.domain.Energy;
import live.ioteatime.batchserver.repository.impl.KwhRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class KwhRepositoryTest {

    @Mock
    InfluxDBClient influxDBClient;

    @Mock
    QueryApi queryApi;

    @InjectMocks
    KwhRepositoryImpl kwhRepository;

    @Test
    void findAllByType() {
        given(influxDBClient.getQueryApi()).willReturn(queryApi);
        given(queryApi.query(anyString())).willReturn(List.of(createDummyFluxTable()));

        List<String> types = List.of("main");

        List<Energy> energyList = kwhRepository.findAllByType(types);

        assertThat(energyList).element(0)
                              .hasFieldOrPropertyWithValue("place", "office")
                              .hasFieldOrPropertyWithValue("type", "main")
                              .hasFieldOrPropertyWithValue("kwh", 22.2);
    }

    private FluxTable createDummyFluxTable() {
        FluxTable fluxTable = new FluxTable();
        FluxRecord fluxRecord = new FluxRecord(0);
        LinkedHashMap<String, Object> values = new LinkedHashMap<>();
        values.put("place", "office");
        values.put("type", "main");
        values.put("_value", 22.2);

        ReflectionTestUtils.setField(fluxRecord, "values", values);
        ReflectionTestUtils.setField(fluxTable, "records", List.of(fluxRecord));

        return fluxTable;
    }
}
