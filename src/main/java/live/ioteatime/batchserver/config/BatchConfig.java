package live.ioteatime.batchserver.config;

import java.util.Date;
import java.util.Map;
import live.ioteatime.batchserver.tasklet.DailyTasklet;
import live.ioteatime.batchserver.tasklet.MonthlyTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private static final String TIME = "time";

    private final StepBuilderFactory stepBuilderFactory;

    private final JobBuilderFactory jobBuilderFactory;

    private final JobLauncher jobLauncher;

    @Bean
    public Job dailyJob() {
        return jobBuilderFactory.get("daily-job")
                                .start(dailyStep())
                                .build();
    }

    @Bean
    public Step dailyStep() {
        return stepBuilderFactory.get("daily-step")
                                 .tasklet(new DailyTasklet())
                                 .build();
    }

    @Bean
    public Job monthlyJob() {
        return jobBuilderFactory.get("monthly-job")
                                .start(monthlyStep())
                                .build();
    }

    @Bean
    public Step monthlyStep() {
        return stepBuilderFactory.get("monthly-step")
                                 .tasklet(new MonthlyTasklet())
                                 .build();
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void daily() throws JobExecutionException {
        jobLauncher.run(dailyJob(), refreshParams());
    }

    @Scheduled(cron = "0 0 0 1 * *")
    private void monthly() throws JobExecutionException {
        jobLauncher.run(monthlyJob(), refreshParams());
    }

    private JobParameters refreshParams() {
        Map<String, JobParameter> parameters = Map.of(
            TIME, new JobParameter(new Date(System.currentTimeMillis()))
        );

        return new JobParameters(parameters);
    }
}