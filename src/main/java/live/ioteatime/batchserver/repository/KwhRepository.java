package live.ioteatime.batchserver.repository;

import live.ioteatime.batchserver.dto.Energy;

import java.util.List;

/*
* InfluxDB 에서 데이터를 조회하는 DAO 입니다.
* */
public interface KwhRepository {

    /**
     * 장소(타입)으로 전력량을 조회하고 Energy 리스트를 메소드입니다.
     *
     * @param type 장소를 나타냅니다.
     * @return 리스트 형태로 Energy(type, kwh) 객체를 반환합니다.
     */
    List<Energy> findAllByType(List<String> type);
}
