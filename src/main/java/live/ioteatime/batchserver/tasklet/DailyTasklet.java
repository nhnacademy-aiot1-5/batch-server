package live.ioteatime.batchserver.tasklet;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import live.ioteatime.batchserver.domain.Channel;
import live.ioteatime.batchserver.domain.Consumption;
import live.ioteatime.batchserver.domain.Energy;
import live.ioteatime.batchserver.repository.ChannelRepository;
import live.ioteatime.batchserver.repository.DailyConsumptionRepository;
import live.ioteatime.batchserver.repository.KwhRepository;
import live.ioteatime.batchserver.util.BillUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyTasklet implements Tasklet {

    private static final String MAIN = "main";
    private static final String TOTAL_KEY = "totalmain";
    private static final int PRECISION = 100;

    private final ChannelRepository channelRepository;

    private final KwhRepository kwhRepository;

    private final DailyConsumptionRepository dailyRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        HashMap<String, Consumption> consumptions = new HashMap<>();

        List<Channel> channels = channelRepository.findAll();
        channels.forEach(channel -> putConsumption(channel, consumptions));
        List<String> types = channels.stream()
                                     .map(Channel::getChannelName)
                                     .collect(Collectors.toUnmodifiableList());

        List<Energy> energies = kwhRepository.findDailyConsumptions(types);
        energies.forEach(energy -> mapConsumption(energy, consumptions));
        calcTotal(energies, consumptions);

        dailyRepository.saveAll(List.copyOf(consumptions.values()));

        return RepeatStatus.FINISHED;
    }

    private void calcTotal(List<Energy> energies, HashMap<String, Consumption> consumptions) {
        double totalKwh = energies.stream()
                                  .filter(energy -> MAIN.equals(energy.getType()))
                                  .mapToDouble(Energy::getKwh)
                                  .sum();
        Consumption totalConsumption = consumptions.get(TOTAL_KEY);
        totalConsumption.setKwh(round(totalKwh));
        totalConsumption.setBill(BillUtils.getDemandCharge(totalKwh));
    }

    private double round(double value) {
        return (double) Math.round(value * PRECISION) / PRECISION;
    }

    private void mapConsumption(Energy energy, HashMap<String, Consumption> consumptions) {
        String key = energy.getPlace() + energy.getType();
        Consumption consumption = consumptions.get(key);

        if (consumption == null) {
            return;
        }

        Double kwh = energy.getKwh();
        consumption.setKwh(round(kwh));
        consumption.setBill(BillUtils.getDemandCharge(kwh));
    }

    private void putConsumption(Channel channel, HashMap<String, Consumption> consumptions) {
        String key = channel.getPlaceName() + channel.getChannelName();
        Consumption consumption = createConsumption(channel);
        consumptions.put(key, consumption);
    }

    private Consumption createConsumption(Channel channel) {
        Integer channelId = channel.getChannelId();
        String channelName = channel.getChannelName();

        return new Consumption(channelId, channelName, null, null);
    }
}
