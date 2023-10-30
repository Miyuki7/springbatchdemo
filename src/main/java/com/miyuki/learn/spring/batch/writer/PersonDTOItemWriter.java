package com.miyuki.learn.spring.batch.writer;

import com.miyuki.learn.spring.batch.dto.PersonDTO;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;


/**
 * @author: miyuki
 * @description: writer
 * @date: 2023/10/25 16:17
 * @version: 1.0
 */
public class PersonDTOItemWriter implements ItemWriter<PersonDTO> {


    @Override
    public void write(List<? extends PersonDTO> items) throws Exception {
        // 写到控制台
        System.out.println("本批次需要写入 " + items.size() + " 条数据");
        for (PersonDTO item : items) {
            System.out.println(item);
        }

        // todo 2 写到另一个文件
        // todo 3 进阶 写到数据库， 表结构自定义
    }
}
