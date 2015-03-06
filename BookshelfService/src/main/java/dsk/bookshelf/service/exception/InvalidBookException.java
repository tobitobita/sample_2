package dsk.bookshelf.service.exception;

public class InvalidBookException extends Exception {

    public InvalidBookException() {
    }

    public InvalidBookException(String message) {
        super(message);
    }

    public InvalidBookException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidBookException(Throwable cause) {
        super(cause);
    }

    public InvalidBookException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
