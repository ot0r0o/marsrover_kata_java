/**
 * Created by Alberto Toro on 24/09/16.
 */
package dev.toro.marsrover.service;

import dev.toro.marsrover.boot.AppRunner;
import dev.toro.marsrover.entity.OrientationType;
import dev.toro.marsrover.entity.Grid;
import dev.toro.marsrover.entity.MovementType;
import dev.toro.marsrover.entity.Rover;
import dev.toro.marsrover.exception.IncorrectDataException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * This class initializes the grid and the rovers to perform the moves
 */
@Component
public class CommandExecutorService {

    private Logger log = Logger.getLogger(CommandExecutorService.class);

    private Grid grid;
    private List<Rover> rovers;

    /**
     * This method initializes a new Grid
     * @param col
     * @param row
     */
    public void initGrid(final int col, final int row){
        log.info(String.format("Init new grid[%s,%s]", col ,row));
        grid = new Grid(col, row);
    }

    /**
     * This method initializes all the rovers
     * @param roversData
     */
    public void initRovers(final List<String[]> roversData) throws IncorrectDataException{

        rovers = new ArrayList<>();

        for (String[] strings : roversData) {
            log.info(String.format("Init new rover[%s, %s]", strings[0], strings[1]));
            StringTokenizer st = new StringTokenizer(strings[0]);
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            OrientationType orientation = null;

            // Check that orientation is correct
            try{
                orientation = OrientationType.valueOf(st.nextToken());
            }catch(IllegalArgumentException e){
                String error = "Illegal orientation on Rover";
                log.error(error);
                throw new IncorrectDataException(error);
            }

            // Check that rover data is correct
            checkRoverData(x, y, strings[1]);

            rovers.add(new Rover(x, y, orientation, strings[1]));
        }
    }

    /**
     * This method executes the rovers movements stored previously
     * @return
     */
    public String executeMovements(){
        StringBuffer sb = new StringBuffer();

        // Start the movements one by one rover in the list
        for (Rover rover : rovers) {
            Arrays.stream(rover.getCommandsToExecute().split("(?!^)")).forEach(move -> {
                moveRover(rover, MovementType.valueOf(move));
            });
            sb.append(rover.toString());
        }

        return sb.toString();
    }

    /**
     * This method checks that all the rover data is correct.
     * Throws {@link IncorrectDataException} on illegal data.
     * @param x
     * @param y
     * @param moves
     */
    private void checkRoverData(final int x, final int y, final String moves)
            throws IncorrectDataException{
        if(grid.outSideGrid(x, y)){
            String error = "Position outside of the grid";
            log.error(error);
            throw new IncorrectDataException(error);
        }

        if(!moves.matches("^[LMR]*$")){
            String error = "Moves of a Rover have illegal command";
            log.error(error);
            throw new IncorrectDataException(error);
        }
    }

    /**
     * This method moves the rover to his new position
     * @param r
     * @param move
     */
    private void moveRover(final Rover r, final MovementType move){

        // If the movement is M, then move one grid based on rovers current direction
        // Otherwise, apply the correct rotation.
        if(move.equals(MovementType.M)){

            int x = r.getX();
            int y = r.getY();
            switch (r.getOrientationType()){
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
            if(!checkNewMovement(x, y)){
                grid.updatePossitiononGrid(r.getX(), r.getY(), x, y);
                r.setX(x);
                r.setY(y);
            }
        }else if(move.equals(MovementType.L) || move.equals(MovementType.R)){
            // Face the rover to his new direction
            r.setOrientationType(
                    r.getOrientationType().newOrientation(move.getValue()));
        }
    }

    /**
     * This method check if the new movement can be performed
     * @param x
     * @param y
     * @return
     */
    private boolean checkNewMovement(final int x, final int y){
        return grid.outSideGrid(x, y) &&  grid.positionOccupied(x, y);
    }
}
