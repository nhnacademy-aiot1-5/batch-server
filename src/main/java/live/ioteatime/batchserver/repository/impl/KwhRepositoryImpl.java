package live.ioteatime.batchserver.repository.impl;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import live.ioteatime.batchserver.domain.Energy;
import live.ioteatime.batchserver.domain.Period;
import live.ioteatime.batchserver.repository.KwhRepository;
import live.ioteatime.batchserver.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class KwhRepositoryImpl implements KwhRepository {

    private final InfluxDBClient influxDBClient;

    @Override
    public List<Energy> findDailyConsumptions(List<String> types) {
        LocalDate date = TimeUtils.getDate();
        String query = getQuery(types, date.minusDays(2), date, Period.DAILY);

        return executeQuery(query);
    }

    @Override
    public List<Energy> findMonthlyConsumptions(List<String> types) {
        LocalDate date = TimeUtils.getDate();
        String query = getQuery(types, date, date, Period.MONTHLY);

        return executeQuery(query);
    }

    private List<Energy> executeQuery(String query) {
        QueryApi queryApi = influxDBClient.getQueryApi();

        return queryApi.query(query)
                       .stream()
                       .flatMap(fluxTable -> fluxTable.getRecords().stream())
                       .map(this::convertToEnergy)
                       .collect(Collectors.toUnmodifiableList());
    }

    private Energy convertToEnergy(FluxRecord fluxRecord) {
        String place = (String) fluxRecord.getValueByKey("place");
        String type = (String) fluxRecord.getValueByKey("type");
        Double value = (Double) fluxRecord.getValue();

        return new Energy(place, type, value);
    }

    private String getQuery(
        List<String> types,
        LocalDate startDate,
        LocalDate stopDate,
        Period period) {

        StringBuilder builder = new StringBuilder();
        builder.append("from(bucket: \"ioteatime\")\n")
               .append("  |> range(start: ").append(startDate).append(period.getStartTime())
               .append(", stop: ").append(stopDate).append(period.getStopTime())
               .append(")\n  |> filter(fn: (r) => ");

        types.stream()
             .limit(1)
             .forEach(type -> builder.append("r[\"type\"] == \"").append(type).append("\""));
        types.stream()
             .skip(1)
             .forEach(type -> builder.append(" or r[\"type\"] == \"").append(type).append("\""));

        builder.append(")\n  |> filter(fn: (r) => r[\"phase\"] == \"kwh\")\n")
               .append("  |> filter(fn: (r) => r[\"description\"] == \"").append(period.getDescription())
               .append("\")\n").append("  |> aggregateWindow(every: ").append(period.getTerm())
               .append(", fn: last, createEmpty: false, offset: 15h)\n")
               .append("  |> ").append(period.getQuery());

        return builder.toString();
    }
}
