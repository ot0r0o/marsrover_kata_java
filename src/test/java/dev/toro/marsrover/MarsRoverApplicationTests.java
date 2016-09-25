package dev.toro.marsrover;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class test the input arguments for the application
 */
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

}
