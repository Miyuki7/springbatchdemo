package com.miyuki.learn.spring.batch.processor;

import com.miyuki.learn.spring.batch.dto.PersonDTO;
import org.springframework.batch.item.ItemProcessor;

/**
 * @author: miyuki
 * @description: processor
 * @date: 2023/10/25 16:20
 * @version: 1.0
 */
public class LineToPersonDTOItemProcessor implements ItemProcessor<String, PersonDTO> {
    @Override
    public PersonDTO process(String item) throws Exception {
        PersonDTO personDTO = new PersonDTO();
        // todo 1 处理每行的内容到PersonDTO
        personDTO.setFirstName(item);
        return personDTO;
    }
}
