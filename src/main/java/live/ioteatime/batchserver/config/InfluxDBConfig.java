package live.ioteatime.batchserver.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import live.ioteatime.batchserver.properties.InfluxDBProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(InfluxDBProperties.class)
public class InfluxDBConfig {

    @Bean
    public InfluxDBClient influxDBClient(InfluxDBProperties properties) {
        return InfluxDBClientFactory.create(
            properties.getUrl(),
            properties.getToken().toCharArray(),
            properties.getOrg(),
            properties.getBucket());
    }
}
