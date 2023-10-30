package com.miyuki.learn.spring.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import java.io.File;

/**
 * @author: miyuki
 * @description:
 * @date: 2023/10/26 11:31
 * @version: 1.0
 */
public class LogFileProcessor implements ItemProcessor<File, File> {
    @Override
    public File process(File item) throws Exception {
        // 在这里可以实现对.log文件的处理，例如复制或读取内容
        // 返回处理后的文件
        return item;
    }
}
