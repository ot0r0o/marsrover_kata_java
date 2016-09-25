package dev.toro.marsrover;

import dev.toro.marsrover.boot.AppRunner;
import dev.toro.marsrover.exception.IncorrectDataException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class MarsRoverApplicationTests {

    @InjectMocks
    private MarsRoverApplication app = new MarsRoverApplication();


    @Test
    public void testNoArgs(){
        String[] args = {""};

        try{
            app.main(args);
        }catch(IllegalStateException e){
            Assert.assertTrue(e.getCause() instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testMoreThanOneArgument(){
        String[] args = {"--file", "--web", "--keyboard"};

        try{
            app.main(args);
        }catch(IllegalStateException e){
            Assert.assertTrue(e.getCause() instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testCommandNotFound() {
        String[] args = {"--test"};

        try{
            app.main(args);
        }catch(IllegalStateException e){
            Assert.assertTrue(e.getCause() instanceof IllegalArgumentException);
        }
    }

	@Test
	public void testCorrectDataLoad() {
        String[] args = {"--file=src/test/resources/input.txt"};
        app.main(args);
	}

	@Test
	public void testInputErrorDirection(){
        String[] args = {"--file=src/test/resources/input_incorrect_direction.txt"};

        try{
            app.main(args);
        }catch(IllegalStateException e){
            Assert.assertTrue(e.getCause() instanceof IncorrectDataException);
        }
    }

    @Test
    public void testInputErrorPosition(){
        String[] args = {"--file=src/test/resources/input_incorrect_position.txt"};

        try{
            app.main(args);
        }catch(IllegalStateException e){
            Assert.assertTrue(e.getCause() instanceof IncorrectDataException);
        }
    }

    @Test
    public void testInputErrorMove(){
        String[] args = {"--file=src/test/resources/input_incorrect_move.txt"};

        try{
            app.main(args);
        }catch(IllegalStateException e){
            Assert.assertTrue(e.getCause() instanceof IncorrectDataException);
        }
    }

}
