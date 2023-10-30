package com.miyuki.learn.spring.batch.writer;

import org.springframework.batch.item.ItemWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author: miyuki
 * @description:
 * @date: 2023/10/26 11:31
 * @version: 1.0
 */
public class LogFileZipWriter implements ItemWriter<File> {
    private final String outputZipFileName;
    private ZipOutputStream zos;

    public LogFileZipWriter(String outputZipFileName) {
        this.outputZipFileName = outputZipFileName;
    }

    @Override
    public void write(List<? extends File> items) throws Exception {
        if (zos == null) {
            zos = new ZipOutputStream(Files.newOutputStream(Paths.get(outputZipFileName)));
        }

        for (File logFile : items) {
            FileInputStream fis = new FileInputStream(logFile);
            ZipEntry zipEntry = new ZipEntry(logFile.getName());
            zos.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
            fis.close();
            zos.closeEntry();
        }
    }

    public void close() throws IOException {
        if (zos != null) {
            zos.close();
        }
    }
}
