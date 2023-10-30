package com.miyuki.learn.spring.batch.configuration;

import com.miyuki.learn.spring.batch.dto.PersonDTO;
import com.miyuki.learn.spring.batch.processor.LineToPersonDTOItemProcessor;
import com.miyuki.learn.spring.batch.reader.LineItemReader;
import com.miyuki.learn.spring.batch.writer.PersonDTOItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * @author: miyuki
 * @description: csvconfig
 * @date: 2023/10/25 16:28
 * @version: 1.0
 */
public class CsvImportJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public CsvImportJobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }
    public Job create() {
        JobBuilder jobBuilder = this.jobBuilderFactory.get("CsvImport");
        return jobBuilder.start(newStep()).build();
    }

    private Step newStep() {
        StepBuilder stepBuilder = this.stepBuilderFactory.get("CsvImportStep");
        return stepBuilder
                // 每次读n行
                .<String, PersonDTO>chunk(3)
                .reader(newReader())
                .processor(new LineToPersonDTOItemProcessor())
                .writer(new PersonDTOItemWriter())
                .build();
    }

    private ItemReader<String> newReader() {
        Path path = Paths.get("person.csv");
        return new LineItemReader(path);
    }

    private ItemWriter<PersonDTO> newWriter() {
        return new PersonDTOItemWriter();
    }
}
