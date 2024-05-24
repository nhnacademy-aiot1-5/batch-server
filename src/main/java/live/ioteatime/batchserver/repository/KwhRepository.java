package live.ioteatime.batchserver.repository;

import live.ioteatime.batchserver.dto.TypeKwh;

import java.util.List;

/*
* InfluxDB 에서 데이터를 조회하는 DAO 입니다./
* */
public interface KwhRepository {

    /**
     * 장소(타입)으로 전력량을 조회하는 메소드입니다.
     *
     * @param type 장소를 나타냅니다.
     * @return 리스트 형태로 TypeKwh(type, kwh) 객체를 반환합니다.
     */
    List<TypeKwh> findAllByType(List<String> type);
}
