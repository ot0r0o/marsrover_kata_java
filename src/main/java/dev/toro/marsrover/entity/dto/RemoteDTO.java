/**
 * Created by Alberto Toro on 25/09/16.
 */
package dev.toro.marsrover.entity.dto;

import java.util.List;

/**
 * This class is used to get needed information from remote controller
 *
 * JSON example: {"grid":"5 5","rovers":[["1 2 N","LMLMLMLMM"],["3 3 E","MMRMMRMRRM"]]}
 */
public class RemoteDTO {

    private String grid;

    private List<String[]> rovers;


    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }

    public List<String[]> getRovers() {
        return rovers;
    }

    public void setRovers(List<String[]> rovers) {
        this.rovers = rovers;
    }
}
