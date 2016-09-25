/**
 * Created by Alberto Toro on 25/09/16.
 */
package dev.toro.marsrover.service;

import dev.toro.marsrover.MarsRoverApplication;
import dev.toro.marsrover.exception.IncorrectDataException;
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
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class test the FileService component and the outputs
 */
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

    @Test
    public void testOutputFileSolutionWithOutsideGrid() throws IOException {
        fileService.makeMovementsByFile("src/test/resources/input_outside_grid.txt");

        // Check file is correctly written
        Assert.assertTrue(Files.exists(Paths.get(outputFile)));

        // Check that file contains correct solution for input.txt
        Assert.assertTrue(Files.readAllLines(Paths.get(outputFile)).stream()
                .findFirst()
                .get().contains("4 0 S 1 1 E"));
    }

    @Test
    public void testOutputFileSolutionWithCollisionAvoid() throws IOException {
        fileService.makeMovementsByFile("src/test/resources/input_collision_avoid.txt");

        // Check file is correctly written
        Assert.assertTrue(Files.exists(Paths.get(outputFile)));

        // Check that file contains correct solution for input.txt
        Assert.assertTrue(Files.readAllLines(Paths.get(outputFile)).stream()
                .findFirst()
                .get().contains("1 2 S 1 1 W"));
    }

    @Test
    public void testInputErrorDirection() throws Exception {
        fileService.makeMovementsByFile("src/test/resources/input_incorrect_direction.txt");
        checkErrorOnOutput();
    }

    @Test
    public void testInputErrorPosition() throws Exception{
        fileService.makeMovementsByFile("src/test/resources/input_incorrect_position.txt");
        checkErrorOnOutput();
    }

    @Test
    public void testInputErrorMove() throws Exception {
        fileService.makeMovementsByFile("src/test/resources/input_incorrect_move.txt");
        checkErrorOnOutput();
    }

    @Test
    public void testInputErrorSamePosition()throws Exception {
        fileService.makeMovementsByFile("src/test/resources/input_incorrect_same_position.txt");
        checkErrorOnOutput();
    }

    private void checkErrorOnOutput() throws IOException {
        Path path = Paths.get(outputFile);

        // Check file is correctly written
        Assert.assertTrue(Files.exists(path));

        // Check that file contains correct solution for input.txt
        Assert.assertTrue(Files.readAllLines(path).stream()
                .findFirst()
                .get().contains("ERROR"));
    }
}
