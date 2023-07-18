package dingulcamping.reservationapp.domain.mail.exception;

public class FailMailException extends RuntimeException{
    public FailMailException() {
        super();
    }

    public FailMailException(String message) {
        super(message);
    }

    public FailMailException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailMailException(Throwable cause) {
        super(cause);
    }
}
