package dev.toro.marsrover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the SpringBootApplication starter. After start, it calls AppRunner to
 * perform argument actions.
 */
@SpringBootApplication
public class MarsRoverApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarsRoverApplication.class, args);
	}
}
