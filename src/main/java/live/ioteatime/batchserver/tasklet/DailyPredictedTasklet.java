package live.ioteatime.batchserver.tasklet;

import live.ioteatime.batchserver.domain.DailyPredictedConsumption;
import live.ioteatime.batchserver.repository.DailyPredictedElectricityConsumptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DailyPredictedTasklet implements Tasklet {

    private final DailyPredictedElectricityConsumptionRepository electricityConsumptionRepository;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        List<DailyPredictedConsumption> predictedConsumptionList = electricityConsumptionRepository.findAll();

        electricityConsumptionRepository.saveAll(predictedConsumptionList);

        return RepeatStatus.FINISHED;
    }
}
