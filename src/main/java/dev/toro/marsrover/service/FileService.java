/**
 * Created by Alberto Toro on 24/09/16.
 */
package dev.toro.marsrover.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Component
public class FileService {

    private Logger log = Logger.getLogger(FileService.class);

    @Autowired
    CommandExecutorService commandExecutorService;

    /**
     * This method starts the rovers movements by input file
     * @param file
     */
    public void makeMovementsByFile(final String file){
        List<String> data = readInputFile(Paths.get(file));

        // As we know, the first line of the file contains the grid size
        // The other lines contains the rover position and movements, in pair

        List<String[]> roversData = new ArrayList<>();
        int i = 0;

        while(i < data.size()){
            // First line
            if(i == 0){
                StringTokenizer st = new StringTokenizer(data.get(0));
                // Init the grid
                commandExecutorService.initGrid(Integer.parseInt(st.nextToken()),
                        Integer.parseInt(st.nextToken()));
                i++;
            }else{
                String[] roverData = new String[2];
                roverData[0] = data.get(i++);
                roverData[1] = data.get(i++);
                roversData.add(roverData);
            }
        }
        // Init all rovers data
        commandExecutorService.initRovers(roversData);

        // Execute movements and get results
        String results = commandExecutorService.executeMovements();

        log.info("Rovers movements results: " + results);
    }

    /**
     * This method reads the input file and returns a list of lines with all the
     * needed information to move the rovers over the grid.
     * @param file
     * @return List of lines, empty lines are cleared
     */
    private List<String> readInputFile(final Path file){
        try {
            return Files.readAllLines(file).stream()
                    .filter(l -> !l.isEmpty())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Could not open file", e.getCause());
            return null;
        }
    }
}
