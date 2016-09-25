/**
 * Created by Alberto Toro on 25/09/16.
 */
package dev.toro.marsrover.service;

import dev.toro.marsrover.MarsRoverApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MarsRoverApplication.class)
@TestPropertySource(value = "classpath:application.properties")
public class FileServiceTest {

    @Autowired
    private FileService fileService;

    @Value("${file.output}")
    private String outputFile;

    @Test
    public void testOutputFileSolution() throws IOException {
        fileService.makeMovementsByFile("src/test/resources/input.txt");

        // Check file is correctly written
        Assert.assertTrue(Files.exists(Paths.get(outputFile)));

        // Check that file contains correct solution for input.txt
        Assert.assertTrue(Files.readAllLines(Paths.get(outputFile)).stream()
                .findFirst()
                .get().contains("1 3 N 5 1 E"));
    }
}
