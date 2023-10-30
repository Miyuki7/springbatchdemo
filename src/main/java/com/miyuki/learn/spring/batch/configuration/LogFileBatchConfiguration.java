package com.miyuki.learn.spring.batch.configuration;

import com.miyuki.learn.spring.batch.processor.LogFileProcessor;
import com.miyuki.learn.spring.batch.reader.LogFileReader;
import com.miyuki.learn.spring.batch.writer.LogFileZipWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.io.File;

/**
 * @author: miyuki
 * @description: zipconfig
 * @date: 2023/10/26 11:28
 * @version: 1.0
 */
public class LogFileBatchConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public LogFileBatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job logFileJob() {
        return jobBuilderFactory.get("logFileJob")
                .start(logFileStep())
                .build();
    }

    @Bean
    public Step logFileStep() {
        return stepBuilderFactory.get("logFileStep")
                .<File, File>chunk(1)
                .reader(logFileReader("/Users/miyuki/Project/java/springbatchdemo"))
                .processor(logFileProcessor())
                .writer(logFileZipWriter("logger.zip"))
                .build();
    }

    @Bean
    @JobScope
    public ItemReader<File> logFileReader(@Value("#{jobParameters['directory']}") String directory) {
        return new LogFileReader(directory);
    }

    @Bean
    @StepScope
    public ItemProcessor<File, File> logFileProcessor() {
        return new LogFileProcessor();
    }

    @Bean
    @StepScope
    public ItemWriter<File> logFileZipWriter(@Value("#{jobParameters['outputFile']}") String outputFile) {
        return new LogFileZipWriter(outputFile);
    }
}
