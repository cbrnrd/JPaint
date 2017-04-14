package jpaint.exceptions;

/**
 * Exception if a logging message is blank
 */
public class NoMessageException extends Exception {

    public NoMessageException(){}

    public NoMessageException(String message){
        super(message);
    }
}
