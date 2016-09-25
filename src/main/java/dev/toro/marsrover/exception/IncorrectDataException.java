/**
 * Created by Alberto Toro on 24/09/16.
 */
package dev.toro.marsrover.exception;

public class IncorrectDataException extends RuntimeException{

    private static String MSG = "Inputted data is not correct, please check it";
    public IncorrectDataException(final String message) {
        super(MSG + ": " + message);
    }
}
