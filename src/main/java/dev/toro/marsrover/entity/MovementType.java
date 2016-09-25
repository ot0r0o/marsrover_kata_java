/**
 * Created by Alberto Toro on 24/09/16.
 */
package dev.toro.marsrover.entity;

public enum MovementType {

    L(-1), R(1), M(0);

    private int value;

    MovementType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
