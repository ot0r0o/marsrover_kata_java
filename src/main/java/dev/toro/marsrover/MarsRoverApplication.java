package dev.toro.marsrover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * This is the SpringBootApplication starter. After start, it calls AppRunner to
 * perform argument actions.
 */
@SpringBootApplication
public class MarsRoverApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MarsRoverApplication.class);

        // This parameter only loads web context if this option is required
        if(!args[0].contains("web")){
            app.setWebEnvironment(false);
        }
        ConfigurableApplicationContext ctx = app.run(args);
    }
}
