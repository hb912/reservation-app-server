package dingulcamping.reservationapp.domain.booking.exception;

public class DisableBookingDateException extends RuntimeException {
    public DisableBookingDateException() {
        super();
    }

    public DisableBookingDateException(String message) {
        super(message);
    }

    public DisableBookingDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DisableBookingDateException(Throwable cause) {
        super(cause);
    }
}
