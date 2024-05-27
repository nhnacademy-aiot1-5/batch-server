package live.ioteatime.batchserver.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "influxdb")
@Getter
@Setter
public class InfluxDBProperties {

    private String token;

    private String url;

    private String org;

    private String bucket;
}
