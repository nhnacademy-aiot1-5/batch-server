package live.ioteatime.batchserver.repository;

import java.util.List;
import live.ioteatime.batchserver.dto.Channel;

/**
 * {@code ChannelRepository} 인터페이스는 데이터베이스에서 채널 목록을 가져오는 메서드를 제공합니다.
 */
public interface ChannelRepository {

    /**
     * 데이터베이스에서 채널 목록을 가져옵니다.
     *
     * @return 채널 ID를 키(Long)로, 채널 이름을 값(String)으로 하는 맵을 반환합니다.
     */
    List<Channel> findAll();
}
