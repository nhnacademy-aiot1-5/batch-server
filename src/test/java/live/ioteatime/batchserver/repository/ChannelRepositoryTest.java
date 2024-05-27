package live.ioteatime.batchserver.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Map;
import live.ioteatime.batchserver.domain.Channel;
import live.ioteatime.batchserver.repository.impl.ChannelRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

@ExtendWith(MockitoExtension.class)
class ChannelRepositoryTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    ChannelRepositoryImpl channelRepository;

    @Test
    void findAll() {
        Integer channelId = 1;
        String channelName = "channel_1";
        Map<String, Object> map = Map.of(
            "channel_id", channelId,
            "channel_name", channelName
        );
        given(jdbcTemplate.queryForList(any())).willReturn(List.of(map));

        List<Channel> channels = channelRepository.findAll();

        assertThat(channels).element(0)
                            .hasFieldOrPropertyWithValue("channelId", channelId)
                            .hasFieldOrPropertyWithValue("channelName", channelName);
    }
}
