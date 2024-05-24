package live.ioteatime.batchserver.repository;

import java.util.List;
import live.ioteatime.batchserver.dto.Consumption;

/**
 * {@code DailyConsumptionRepository} 인터페이스는 데이터베이스에 일별 전력 사용량을 저장하는 메서드를 제공합니다.
 */
public interface DailyConsumptionRepository {

    /**
     * 주어진 모든 {@code Consumption} 객체를 저장소에 저장합니다.
     *
     * @param consumptions 저장할 {@code Consumption} 객체들의 리스트
     * @return 모든 {@code Consumption} 객체가 성공적으로 저장되면 {@code true}, 하나라도 저장에 실패하면 {@code false}
     * @throws IllegalArgumentException {@code consumptions}가 null이거나 빈 리스트인 경우
     */
    Boolean saveAll(List<Consumption> consumptions);
}
