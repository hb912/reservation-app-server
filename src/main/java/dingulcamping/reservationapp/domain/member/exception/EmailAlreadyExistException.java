package dingulcamping.reservationapp.domain.member.exception;

public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException() {
        super();
    }

    public EmailAlreadyExistException(String message) {
        super(message);
    }

    public EmailAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
