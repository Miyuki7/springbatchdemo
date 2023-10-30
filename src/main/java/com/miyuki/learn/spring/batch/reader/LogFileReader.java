package com.miyuki.learn.spring.batch.reader;

import org.springframework.batch.item.ItemReader;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author: miyuki
 * @description:
 * @date: 2023/10/26 11:30
 * @version: 1.0
 */
public class LogFileReader implements ItemReader<File> {
    private Queue<File> logFilesQueue = new ArrayDeque<>();

    public LogFileReader(String directory) {
        // 在构造函数中递归扫描指定目录下的.log文件
        scanForLogFiles(new File(directory));
    }

    @Override
    public File read() {
        if (!logFilesQueue.isEmpty()) {
            return logFilesQueue.poll();
        } else {
            return null;
        }
    }

    private void scanForLogFiles(File directory) {
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        scanForLogFiles(file); // 递归扫描子目录
                    } else if (file.getName().endsWith(".log")) {
                        logFilesQueue.offer(file);
                    }
                }
            }
        }
    }
}
