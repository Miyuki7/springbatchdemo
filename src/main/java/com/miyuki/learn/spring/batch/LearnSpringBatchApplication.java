package com.miyuki.learn.spring.batch;

import com.miyuki.learn.spring.batch.configuration.CsvImportJobConfiguration;
import com.miyuki.learn.spring.batch.configuration.FirstJobConfiguration;
import com.miyuki.learn.spring.batch.configuration.LogFileBatchConfiguration;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Date;

/**
 * @author: miyuki
 * @description: main
 * @date: 2023/10/25 16:36
 * @version: 1.0
 */
@SpringBootApplication
@ComponentScan("com.miyuki")
public class LearnSpringBatchApplication {

    public static void main(String[] args) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        defineAndRunFirstJob();
        defineAndRunCsvImportJob();
//        defineAndRunZipJob();
    }

    /**
     * 定义和运行第一个批处理任务
     */
    static void defineAndRunFirstJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        // 初始上下文，感受一下手动完成依赖注入是多麻烦
        PlatformTransactionManager transactionManager = new ResourcelessTransactionManager();
        // 设置Job的状态保存方式
        JobRepository jobRepository = createJobRepository(transactionManager);

        // 创建第一个Job
        FirstJobConfiguration firstJobConfiguration = new FirstJobConfiguration(
                new JobBuilderFactory(jobRepository),
                new StepBuilderFactory(jobRepository, transactionManager)
        );
        Job job = firstJobConfiguration.create();

        // 运行
        JobLauncher jobLauncher = createJobLauncher(jobRepository);
        // 一个Job可以运行多次，
        // 每次运行时使用的JobParameters都不一样
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("now", new Date())
                .addString("myName", "xxx")
                .toJobParameters();
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        // 获取Job退出时的状态
        System.out.println("job " + job.getName() + "exit status " + jobExecution.getExitStatus());
    }

    static void defineAndRunCsvImportJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        PlatformTransactionManager transactionManager = new ResourcelessTransactionManager();
        JobRepository jobRepository = createJobRepository(transactionManager);

        // 创建第一个Job
        CsvImportJobConfiguration configuration = new CsvImportJobConfiguration(
                new JobBuilderFactory(jobRepository),
                new StepBuilderFactory(jobRepository, transactionManager)
        );
        Job job = configuration.create();
        // 运行
        JobLauncher jobLauncher = createJobLauncher(jobRepository);
        jobLauncher.run(job, new JobParameters());
    }

    static void defineAndRunZipJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        PlatformTransactionManager transactionManager = new ResourcelessTransactionManager();
        JobRepository jobRepository = createJobRepository(transactionManager);

        // 创建第一个Job
        LogFileBatchConfiguration configuration = new LogFileBatchConfiguration(
                new JobBuilderFactory(jobRepository),
                new StepBuilderFactory(jobRepository, transactionManager)
        );
        Job job = configuration.logFileJob();
        // 运行
        JobLauncher jobLauncher = createJobLauncher(jobRepository);
        jobLauncher.run(job, new JobParameters());
    }

    /**
     * 创建一个不需要数据库的spring batch运行时
     * todo 5 进阶 使用spring boot来自动注入，创建相关的bean
     */
    private static JobRepository createJobRepository(PlatformTransactionManager transactionManager) {
        MapJobRepositoryFactoryBean jobRepositoryFactoryBean = new MapJobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setTransactionManager(transactionManager);
        try {
            return jobRepositoryFactoryBean.getObject();
        } catch (Exception e) {
            throw new IllegalStateException("create JobRepository fail", e);
        }
    }

    static JobLauncher createJobLauncher(JobRepository jobRepository) {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        try {
            jobLauncher.afterPropertiesSet();
        } catch (Exception e) {
            throw new IllegalStateException("init SimpleJobLauncher fail", e);
        }
        return jobLauncher;
    }
}
