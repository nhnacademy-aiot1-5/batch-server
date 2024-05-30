package live.ioteatime.batchserver.tasklet;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import live.ioteatime.batchserver.domain.Channel;
import live.ioteatime.batchserver.domain.Consumption;
import live.ioteatime.batchserver.domain.Energy;
import live.ioteatime.batchserver.repository.ChannelRepository;
import live.ioteatime.batchserver.repository.KwhRepository;
import live.ioteatime.batchserver.repository.MonthlyConsumptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonthlyTasklet extends AbstractTasklet {

    private final ChannelRepository channelRepository;

    private final KwhRepository kwhRepository;

    private final MonthlyConsumptionRepository monthlyRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        HashMap<String, Consumption> consumptions = new HashMap<>();

        List<Channel> channels = channelRepository.findAll();
        channels.forEach(channel -> putConsumption(channel, consumptions));
        List<String> types = channels.stream()
                                     .map(Channel::getChannelName)
                                     .collect(Collectors.toUnmodifiableList());

        List<Energy> energies = kwhRepository.findMonthlyConsumptions(types);
        energies.forEach(energy -> mapConsumption(energy, consumptions));
        calcTotal(energies, consumptions);

        monthlyRepository.saveAll(List.copyOf(consumptions.values()));

        return RepeatStatus.FINISHED;
    }
}
