package dingulcamping.reservationapp.domain.booking.exception;

public class InvalidStartDate extends RuntimeException {
    public InvalidStartDate() {
        super();
    }

    public InvalidStartDate(String message) {
        super(message);
    }

    public InvalidStartDate(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidStartDate(Throwable cause) {
        super(cause);
    }
}
