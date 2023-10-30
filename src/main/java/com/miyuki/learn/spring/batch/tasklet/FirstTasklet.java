package com.miyuki.learn.spring.batch.tasklet;

import com.sun.jmx.snmp.tasks.Task;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * @author: miyuki
 * @description: tasklet
 * @date: 2023/10/25 15:50
 * @version: 1.0
 */
public class FirstTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        System.out.println("hello, world! by tasklet");
        // 获取 JobParameters
        chunkContext
                .getStepContext()
                .getJobParameters()
                .forEach((k, v) -> System.out.println("job parameter " + k + " = " + v));
        return RepeatStatus.FINISHED;
    }
}
