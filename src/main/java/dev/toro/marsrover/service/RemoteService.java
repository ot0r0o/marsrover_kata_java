/**
 * Created by Alberto Toro on 25/09/16.
 */
package dev.toro.marsrover.service;

import dev.toro.marsrover.entity.dto.RemoteDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Service
public class RemoteService {

    private Logger log = Logger.getLogger(RemoteService.class);

    @Autowired
    private CommandExecutorService commandExecutorService;

    /**
     * This method receives the remote data to be processed and executed
     * @param remote Check {@link RemoteDTO}
     * @return Final position and direction for each rover
     */
    public String makeMovementsByRemote(RemoteDTO remote) {

        // Init the grid
        StringTokenizer st = new StringTokenizer(remote.getGrid());
        commandExecutorService.initGrid(Integer.parseInt(st.nextToken()),
                Integer.parseInt(st.nextToken()));

        // Init all rovers data
        List<String[]> roversData = new ArrayList<>();
        remote.getRovers().forEach(roversData::add);
        commandExecutorService.initRovers(roversData);

        // Execute movements and get results
        String results = commandExecutorService.executeMovements();
        log.info("Rovers movements results: " + results);
        return results;
    }

    /**
     * This method checks the movements of the rovers
     * @return Final position and direction for each rover
     */
    public String check(){
        return commandExecutorService.executeMovements();
    }
}
