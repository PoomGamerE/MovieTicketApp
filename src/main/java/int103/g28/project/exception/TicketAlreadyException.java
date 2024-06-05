package int103.g28.project.exception;

public class TicketAlreadyException extends RuntimeException {
    public TicketAlreadyException() {
    }

    public TicketAlreadyException(String message) {
        super(message);
    }

    public TicketAlreadyException(Exception cause) {
        super(cause);
    }

    public TicketAlreadyException(String message, Exception cause) {
        super(message, cause);
    }
}