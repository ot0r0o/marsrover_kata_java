/**
 * Created by Alberto Toro on 24/09/16.
 */
package dev.toro.marsrover.boot;

import dev.toro.marsrover.service.FileService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * This class is used to perform the actions specified in the
 * application arguments
 */
@Component
public class AppRunner implements ApplicationRunner {

    private Logger log = Logger.getLogger(AppRunner.class);

    @Autowired
    private FileService fileService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(args.getOptionNames().isEmpty()){
            throw new IllegalArgumentException("Please specify a command: --file, --keyboard or --web");
        }else if(args.getOptionNames().size() > 1){
            throw new IllegalArgumentException("Only one data source at a time");
        }

        if(args.containsOption("web")){

        }else if(args.containsOption("keyboard")){

        }else if(args.containsOption("file")){
            fileService.makeMovementsByFile(args.getOptionValues("file").get(0));
        }else{
            throw new IllegalArgumentException("Command not found");
        }

    }
}
