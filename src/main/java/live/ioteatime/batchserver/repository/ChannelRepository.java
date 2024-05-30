package live.ioteatime.batchserver.repository;

import java.util.List;
import live.ioteatime.batchserver.domain.Channel;

/**
 * {@code ChannelRepository} 인터페이스는 데이터베이스에서 채널 목록을 가져오는 메서드를 제공합니다.
 */
public interface ChannelRepository {

    /**
     * 저장소에서 모든 {@code Channel} 객체를 검색하여 반환합니다.
     *
     * @return 모든 {@code Channel} 객체의 리스트
     */
    List<Channel> findAll();
}
