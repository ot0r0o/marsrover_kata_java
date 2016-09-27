/**
 * Created by Alberto Toro on 25/09/16.
 */
package dev.toro.marsrover.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

@Service
public class KeyboardService {

    private Logger log = Logger.getLogger(KeyboardService.class);

    @Autowired
    private CommandExecutorService commandExecutorService;

    private Scanner scanner;

    /**
     * This method starts the rovers movements by keyboard input
     */
    public void makeMovementsByKeyboard(){
        scanner = new Scanner(System.in);

        // Init the grid
        System.out.println("* Insert upper-right of the grid (Ex: '5 5')");
        StringTokenizer st = new StringTokenizer(scanner.nextLine());
        commandExecutorService.initGrid(Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken()));

        // Init rovers
        List<String[]> roversData = new ArrayList<>();
        do{
            String[] roverData = new String[2];
            System.out.println("* Insert rover position (Ex: '1 2 N')");
            roverData[0] = scanner.nextLine();
            System.out.println("* Insert rover movements (Ex: 'LRM...')");
            roverData[1] = scanner.nextLine();
            roversData.add(roverData);

            System.out.println("* Do you want to insert new Rover data? (Y / N)");
        }while(!scanner.nextLine().equalsIgnoreCase("N"));

        // Init all rovers data
        commandExecutorService.initRovers(roversData);

        // Execute movements and get results
        String results = commandExecutorService.executeMovements();

        log.info("Rovers movements results: " + results);
    }
}
