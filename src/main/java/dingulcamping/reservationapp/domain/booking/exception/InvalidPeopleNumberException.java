package dingulcamping.reservationapp.domain.booking.exception;

public class InvalidPeopleNumberException extends RuntimeException {
    public InvalidPeopleNumberException() {
        super();
    }

    public InvalidPeopleNumberException(String message) {
        super(message);
    }

    public InvalidPeopleNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPeopleNumberException(Throwable cause) {
        super(cause);
    }
}
