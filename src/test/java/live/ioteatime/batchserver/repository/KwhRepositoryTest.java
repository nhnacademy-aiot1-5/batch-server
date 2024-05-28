package live.ioteatime.batchserver.repository;

import com.influxdb.client.InfluxDBClient;
import java.util.List;
import live.ioteatime.batchserver.domain.Energy;
import live.ioteatime.batchserver.repository.impl.KwhRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KwhRepositoryTest {

    @Mock
    InfluxDBClient influxDBClient;

    @InjectMocks
    KwhRepositoryImpl kwhRepository;

    @Test
    void findAllByType() {
        kwhRepository.findAllByType(List.of("main", "ac", "ac_indoor_unit"))
                     .stream()
                     .map(Energy::getKwh)
                     .forEach(System.out::println);
    }
}
