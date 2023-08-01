package dingulcamping.reservationapp.domain.booking.exception;

public class NotExistBookingException extends RuntimeException{
    public NotExistBookingException() {
        super();
    }

    public NotExistBookingException(String message) {
        super(message);
    }

    public NotExistBookingException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistBookingException(Throwable cause) {
        super(cause);
    }
}
