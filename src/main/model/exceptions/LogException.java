package model.exceptions;

public class LogException extends Exception {
    public LogException() {
        super("An error occurred while trying to print log");
    }

    public LogException(String msg) {
        super(msg);
    }
}
