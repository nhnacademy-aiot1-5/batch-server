package live.ioteatime.batchserver.tasklet;

import java.util.HashMap;
import java.util.List;
import live.ioteatime.batchserver.domain.Channel;
import live.ioteatime.batchserver.domain.Consumption;
import live.ioteatime.batchserver.domain.Energy;
import live.ioteatime.batchserver.util.BillUtils;
import org.springframework.batch.core.step.tasklet.Tasklet;

public abstract class AbstractTasklet implements Tasklet {

    private static final String MAIN = "main";
    private static final String TOTAL_KEY = "totalmain";
    private static final int PRECISION = 100;

    protected void calcTotal(List<Energy> energies, HashMap<String, Consumption> consumptions) {
        double totalKwh = energies.stream()
                                  .filter(energy -> MAIN.equals(energy.getType()))
                                  .mapToDouble(Energy::getKwh)
                                  .sum();
        Consumption totalConsumption = consumptions.get(TOTAL_KEY);
        totalConsumption.setKwh(round(totalKwh));
        totalConsumption.setBill(BillUtils.getDemandCharge(totalKwh));
    }

    protected double round(double value) {
        return (double) Math.round(value * PRECISION) / PRECISION;
    }

    protected void mapConsumption(Energy energy, HashMap<String, Consumption> consumptions) {
        String key = energy.getPlace() + energy.getType();
        Consumption consumption = consumptions.get(key);

        if (consumption == null) {
            return;
        }

        Double kwh = energy.getKwh();
        consumption.setKwh(round(kwh));
        consumption.setBill(BillUtils.getDemandCharge(kwh));
    }

    protected void putConsumption(Channel channel, HashMap<String, Consumption> consumptions) {
        String key = channel.getPlaceName() + channel.getChannelName();
        Consumption consumption = createConsumption(channel);
        consumptions.put(key, consumption);
    }

    protected Consumption createConsumption(Channel channel) {
        Integer channelId = channel.getChannelId();
        String channelName = channel.getChannelName();

        return new Consumption(channelId, channelName, null, null);
    }
}
