package int103.g28.project.exception;

public class ShowtimeAlreadyException extends RuntimeException {
    public ShowtimeAlreadyException() {
    }

    public ShowtimeAlreadyException(String message) {
        super(message);
    }

    public ShowtimeAlreadyException(Exception cause) {
        super(cause);
    }

    public ShowtimeAlreadyException(String message, Exception cause) {
        super(message, cause);
    }
}