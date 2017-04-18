package jpaint.exceptions;

import jpaint.helpers;

/**
 * An exception used in <code>util</code> alert functions.
 * <p></p>
 * This is used when an alert fails to show.
 */
public class AlertException extends Exception{

    public AlertException(){}

    public AlertException(String message){
        super(message);
    }
}
