package com.miyuki.learn.spring.batch.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author: miyuki
 * @description: reader
 * @date: 2023/10/25 15:53
 * @version: 1.0
 */
public class LineItemReader implements ItemReader<String> {

    private final Path path;
    private final BufferedReader bufferedReader;

    public LineItemReader(Path path) {
        this.path = path;
        try {
            this.bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            Files.newInputStream(path),
                            StandardCharsets.UTF_8
                    )
            );
        } catch (IOException e) {
            throw new IllegalStateException("cannot open file " + path.toAbsolutePath(), e);
        }
    }
    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return bufferedReader.readLine();
    }
}
