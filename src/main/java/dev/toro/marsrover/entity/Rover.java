/**
 * Created by Alberto Toro on 24/09/16.
 */
package dev.toro.marsrover.entity;

public class Rover {

    /* This is the current X position on the Grid */
    private int x;

    /* This is the current Y position on the Grid */
    private int y;

    /* This is the current orientation position on the Grid */
    private OrientationType orientationType;

    /* This is the command line to be executed */
    private String commandsToExecute;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public OrientationType getOrientationType() {
        return orientationType;
    }

    public void setOrientationType(OrientationType orientationType) {
        this.orientationType = orientationType;
    }

    public String getCommandsToExecute() {
        return commandsToExecute;
    }

    public void setCommandsToExecute(String commandsToExecute) {
        this.commandsToExecute = commandsToExecute;
    }

    public Rover(int x, int y, OrientationType facing, String commandsToExecute) {
        this.x = x;
        this.y = y;
        this.orientationType = facing;
        this.commandsToExecute = commandsToExecute;
    }

    /**
     * This method will return the current rover position over the grid
     * @return
     */
    @Override
    public String toString() {
        return x + " " + y + " " + orientationType.toString() + " ";
    }
}
