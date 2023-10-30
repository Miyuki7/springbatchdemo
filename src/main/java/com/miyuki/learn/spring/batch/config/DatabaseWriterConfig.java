package com.miyuki.learn.spring.batch.config;

import com.miyuki.learn.spring.batch.dto.PersonDTO;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author: miyuki
 * @description: config
 * @date: 2023/10/30 09:27
 * @version: 1.0
 */
@Configuration
public class DatabaseWriterConfig {

    private DataSource dataSource;

    @Autowired
    public DatabaseWriterConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public JdbcBatchItemWriter<PersonDTO> databaseItemWriter() {
        JdbcBatchItemWriter<PersonDTO> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setItemPreparedStatementSetter((item, ps) -> {
            ps.setString(1, item.getFirstName());
            ps.setString(2, item.getLastName());
            ps.setInt(3, item.getAge());
        });
        itemWriter.setSql("INSERT INTO person (first_name, last_name, age) VALUES (?, ?, ?)");
        return itemWriter;
    }
}
