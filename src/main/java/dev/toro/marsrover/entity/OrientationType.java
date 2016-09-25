/**
 * Created by Alberto Toro on 24/09/16.
 */
package dev.toro.marsrover.entity;

public enum OrientationType {

    N, E, S, W;

    /**
     * This method returns the new orientation based on rovers movements left (-1) and right (1)
     * @param value
     * @return
     */
    public OrientationType newOrientation(int value){
        // Make it circular
        return (ordinal() == 0 && value == -1) ? W :
                values()[(ordinal() + value) % values().length];
    }
}
