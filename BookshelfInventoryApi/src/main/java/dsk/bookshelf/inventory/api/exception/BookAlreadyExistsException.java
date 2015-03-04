package dsk.bookshelf.inventory.api.exception;

public class BookAlreadyExistsException extends Exception {

    public BookAlreadyExistsException() {
    }

    public BookAlreadyExistsException(String message) {
        super(message);
    }

    public BookAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public BookAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
