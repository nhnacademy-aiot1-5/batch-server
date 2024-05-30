package live.ioteatime.batchserver.repository.impl;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import live.ioteatime.batchserver.domain.Energy;
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
        String query = getQuery(types, date.minusDays(2), date, "sum", "difference()");

        return executeQuery(query);
    }

    @Override
    public List<Energy> findMonthlyConsumptions(List<String> types) {
        LocalDate date = TimeUtils.getDate();
        String query = getQuery(types, date.minusDays(1), date, "this_month", "yield(name: \"mean\")");

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
        String description,
        String query) {

        StringBuilder builder = new StringBuilder();
        builder.append("from(bucket: \"ioteatime\")\n")
               .append("  |> range(start: ").append(startDate).append("T15:00:00Z, stop: ").append(stopDate)
               .append("T15:00:00Z)\n")
               .append("  |> filter(fn: (r) => ");

        types.stream()
             .limit(1)
             .forEach(type -> builder.append("r[\"type\"] == \"").append(type).append("\""));
        types.stream()
             .skip(1)
             .forEach(type -> builder.append(" or r[\"type\"] == \"").append(type).append("\""));

        builder.append(")\n  |> filter(fn: (r) => r[\"phase\"] == \"kwh\")\n")
               .append("  |> filter(fn: (r) => r[\"description\"] == \"").append(description).append("\")\n")
               .append("  |> aggregateWindow(every: 24h, fn: last, createEmpty: false, offset: 15h)\n")
               .append("  |> ").append(query);

        return builder.toString();
    }
}
