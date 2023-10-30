package com.miyuki.learn.spring.batch.configuration;

import com.miyuki.learn.spring.batch.tasklet.FirstTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;

/**
 * @author: miyuki
 * @description: config
 * @date: 2023/10/25 16:22
 * @version: 1.0
 */
public class FirstJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public FirstJobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    public Job create() {
        JobBuilder jobBuilder = this.jobBuilderFactory
                .get("FirstJob");
        return jobBuilder.start(stepWithTasklet())
                .build();
    }

    private Step stepWithTasklet() {
        StepBuilder stepBuilder = this.stepBuilderFactory.get("firstStep");
        return stepBuilder.tasklet(new FirstTasklet()).build();
    }
}
