/**
 * Created by Alberto Toro on 24/09/16.
 */
package dev.toro.marsrover.service;

import dev.toro.marsrover.entity.Grid;
import dev.toro.marsrover.entity.MovementType;
import dev.toro.marsrover.entity.OrientationType;
import dev.toro.marsrover.entity.Rover;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * This class initializes the grid and the rovers to perform the moves
 */
@Service
public class CommandExecutorService {

    private Logger log = Logger.getLogger(CommandExecutorService.class);

    private Grid grid;
    private List<Rover> rovers;

    /**
     * This method initializes a new Grid
     *
     * @param col
     * @param row
     */
    public void initGrid(final int col, final int row) {
        log.info(String.format("Init new grid[%s,%s]", col, row));
        grid = new Grid(col, row);
    }

    /**
     * This method initializes all the rovers. Deploy of the rovers is made before movements.
     *
     * @param roversData
     */
    public void initRovers(final List<String[]> roversData) {

        rovers = new ArrayList<>();

        for (String[] strings : roversData) {
            log.info(String.format("Init new rover[%s, %s]", strings[0], strings[1]));
            StringTokenizer st = new StringTokenizer(strings[0]);
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            OrientationType orientation = null;

            // Check that orientation is correct
            try {
                orientation = OrientationType.valueOf(st.nextToken());
            } catch (IllegalArgumentException e) {
                String error = "Illegal orientation on Rover";
                log.error(error);
                rovers.add(new Rover());
                continue;
            }

            // Check that rover data is correct
            if(!checkRoverData(x, y, strings[1])){
                rovers.add(new Rover());
                continue;
            }

            // Mark position on Grid
            grid.updatePositionOnGrid(0, 0, x, y);
            rovers.add(new Rover(x, y, orientation, strings[1]));
        }
    }

    /**
     * This method executes the rovers movements stored previously
     *
     * @return
     */
    public String executeMovements() {
        StringBuffer sb = new StringBuffer();

        // Start the movements one by one rover in the list
        for (Rover rover : rovers) {
            if(!rover.isError()){
                rover.getCommandsToExecute().chars()
                        .mapToObj(c -> String.valueOf((char) c)) // Get each char  and map in String
                        .forEach(move -> // Make move
                                moveRover(rover, MovementType.valueOf(move))
                        );
            }

            sb.append(rover.toString());
        }

        return sb.toString();
    }

    /**
     * This method checks that all the rover data is correct.
     * @param x
     * @param y
     * @param moves
     * @return True when all data is correct. False otherwise
     */
    private boolean checkRoverData(final int x, final int y, final String moves) {
        // Check if the rover position is out side of the grid
        if (grid.outSideGrid(x, y)) {
            String error = "Position outside of the grid";
            log.error(error);
            return false;
        }

        // Check if the rover position if occupied by another one
        if(grid.positionOccupied(x, y)){
            String error = "There are two rovers in the same position";
            log.error(error);
            return false;
        }

        // With an regex, check if the commands only contains the valid characters L R M
        if (!moves.matches("^[LMR]*$")) {
            String error = "Moves of a Rover have illegal command";
            log.error(error);
            return false;
        }

        return true;
    }

    /**
     * This method moves the rover to his new position
     *
     * @param r
     * @param move
     */
    private void moveRover(final Rover r, final MovementType move) {

        // If the movement is M, then move one grid based on rovers current direction
        // Otherwise, apply the correct rotation.
        if (move.equals(MovementType.M)) {
            // Apply move based on direction
            int x = r.getX();
            int y = r.getY();
            switch (r.getOrientationType()) {
                case N:
                    y++;
                    break;
                case E:
                    x++;
                    break;
                case S:
                    y--;
                    break;
                case W:
                    x--;
                    break;
            }

            // If the new movement is outside of the grid or the new grid position is occupied,
            // keep current position
            if (checkNewMovement(x, y)) {
                grid.updatePositionOnGrid(r.getX(), r.getY(), x, y);
                r.setX(x);
                r.setY(y);
            }
        } else if (move.equals(MovementType.L) || move.equals(MovementType.R)) {
            // Face the rover to his new direction
            r.setOrientationType(
                    r.getOrientationType().newOrientation(move.getValue()));
        }
    }

    /**
     * This method check if the new movement can be performed
     *
     * @param x
     * @param y
     * @return
     */
    private boolean checkNewMovement(final int x, final int y) {
        return !grid.outSideGrid(x, y) && !grid.positionOccupied(x, y);
    }
}
