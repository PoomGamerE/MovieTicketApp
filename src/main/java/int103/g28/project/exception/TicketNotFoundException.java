package int103.g28.project.exception;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException() {
    }

    public TicketNotFoundException(String message) {
        super(message);
    }

    public TicketNotFoundException(Exception cause) {
        super(cause);
    }

    public TicketNotFoundException(String message, Exception cause) {
        super(message, cause);
    }
}