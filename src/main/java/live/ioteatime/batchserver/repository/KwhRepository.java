package live.ioteatime.batchserver.repository;

import java.util.List;
import live.ioteatime.batchserver.domain.Energy;

/**
 * InfluxDB 에서 데이터를 조회하는 DAO 입니다.
 */
public interface KwhRepository {

    /**
     * 장소(타입)으로 일별 전력량을 조회하고 Energy 리스트를 메소드입니다.
     *
     * @param types 장소를 나타냅니다.
     * @return 리스트 형태로 Energy(type, kwh) 객체를 반환합니다.
     */
    List<Energy> findDailyConsumptions(List<String> types);

    /**
     * 장소(타입)으로 월별 전력량을 조회하고 Energy 리스트를 메소드입니다.
     *
     * @param types 장소를 나타냅니다.
     * @return 리스트 형태로 Energy(type, kwh) 객체를 반환합니다.
     */
    List<Energy> findMonthlyConsumptions(List<String> types);
}
