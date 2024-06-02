package int103.g28.project.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException() {
    }

    public MovieNotFoundException(String message) {
        super(message);
    }

    public MovieNotFoundException(Exception cause) {
        super(cause);
    }

    public MovieNotFoundException(String message, Exception cause) {
        super(message, cause);
    }
}