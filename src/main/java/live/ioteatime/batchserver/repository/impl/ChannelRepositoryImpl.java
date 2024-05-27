package live.ioteatime.batchserver.repository.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import live.ioteatime.batchserver.domain.Channel;
import live.ioteatime.batchserver.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChannelRepositoryImpl implements ChannelRepository {

    private static final String FIND_ALL_SQL = "select channel_id, channel_name from channels";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Channel> findAll() {
        List<Map<String, Object>> channels = jdbcTemplate.queryForList(FIND_ALL_SQL);

        return channels.stream()
                       .map(this::mapToChannel)
                       .collect(Collectors.toUnmodifiableList());
    }

    private Channel mapToChannel(Map<String, Object> map) {
        Integer channelId = (Integer) map.get("channel_id");
        String channelName = (String) map.get("channel_name");

        return new Channel(channelId, channelName);
    }
}
