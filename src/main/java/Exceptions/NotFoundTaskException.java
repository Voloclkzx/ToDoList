package Exceptions;

public class NotFoundTaskException extends RuntimeException {
    public NotFoundTaskException(String message) {
        super(message);
    }
}
