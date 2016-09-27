/**
 * Created by Alberto Toro on 24/09/16.
 */
package dev.toro.marsrover.boot;

import dev.toro.marsrover.service.FileService;
import dev.toro.marsrover.service.KeyboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is used to perform the actions specified in the
 * application arguments
 */
@Component
public class AppRunner implements ApplicationRunner {

    @Autowired
    private FileService fileService;
    @Autowired
    private KeyboardService keyboardService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (args.getOptionNames().isEmpty()) {
            throw new IllegalArgumentException("Please specify a command: --file, --keyboard or --web");
        } else if (args.getOptionNames().size() > 1) {
            throw new IllegalArgumentException("Only one data source at a time");
        }

        if (args.containsOption("web")) {
            // DO NOTHING, Web context is already loaded
        } else if (args.containsOption("keyboard")) {
            // Let's allow SpringBoot finish the starting process by using threading
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) { }
                while(true){
                    System.out.println("");
                    keyboardService.makeMovementsByKeyboard();
                }
            });
        } else if (args.containsOption("file")) {
            fileService.makeMovementsByFile(args.getOptionValues("file").get(0));
        } else {
            throw new IllegalArgumentException("Command not found");
        }
    }
}
