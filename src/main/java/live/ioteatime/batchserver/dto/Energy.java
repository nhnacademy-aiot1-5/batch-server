package live.ioteatime.batchserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Energy {

    private String type;
    private Double kwh;
}
