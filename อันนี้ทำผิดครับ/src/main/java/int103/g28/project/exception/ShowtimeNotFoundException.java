package int103.g28.project.exception;

public class ShowtimeNotFoundException extends RuntimeException {
    public ShowtimeNotFoundException() {
    }

    public ShowtimeNotFoundException(String message) {
        super(message);
    }

    public ShowtimeNotFoundException(Exception cause) {
        super(cause);
    }

    public ShowtimeNotFoundException(String message, Exception cause) {
        super(message, cause);
    }
}