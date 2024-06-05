package int103.g28.project.exception;

public class MovieAlreadyException extends RuntimeException {
    public MovieAlreadyException() {
    }

    public MovieAlreadyException(String message) {
        super(message);
    }

    public MovieAlreadyException(Exception cause) {
        super(cause);
    }

    public MovieAlreadyException(String message, Exception cause) {
        super(message, cause);
    }
}