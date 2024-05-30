package live.ioteatime.batchserver.tasklet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import live.ioteatime.batchserver.domain.Channel;
import live.ioteatime.batchserver.domain.Energy;
import live.ioteatime.batchserver.repository.ChannelRepository;
import live.ioteatime.batchserver.repository.DailyConsumptionRepository;
import live.ioteatime.batchserver.repository.KwhRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DailyTaskletTest {

    @Mock
    ChannelRepository channelRepository;

    @Mock
    KwhRepository kwhRepository;

    @Mock
    DailyConsumptionRepository dailyRepository;

    @InjectMocks
    DailyTasklet dailyTasklet;

    @Test
    void execute() {
        List<Channel> channels = List.of(
            new Channel(-1, "total", "main"),
            new Channel(1, "office", "main"),
            new Channel(2, "class_a", "main")
        );
        given(channelRepository.findAll()).willReturn(channels);
        List<Energy> energy = List.of(
            new Energy("office", "main", 100.0),
            new Energy("class_a", "main", 100.0),
            new Energy("class_a", "ac", 100.0)
        );
        given(kwhRepository.findDailyConsumptions(any())).willReturn(energy);

        dailyTasklet.execute(null, null);

        verify(channelRepository, times(1)).findAll();
        verify(kwhRepository, times(1)).findDailyConsumptions(any());
        verify(dailyRepository, times(1)).saveAll(any());
    }
}
