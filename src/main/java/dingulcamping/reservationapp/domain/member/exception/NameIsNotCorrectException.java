package dingulcamping.reservationapp.domain.member.exception;

public class NameIsNotCorrectException extends RuntimeException {
    public NameIsNotCorrectException() {
        super();
    }

    public NameIsNotCorrectException(String message) {
        super(message);
    }

    public NameIsNotCorrectException(String message, Throwable cause) {
        super(message, cause);
    }

    public NameIsNotCorrectException(Throwable cause) {
        super(cause);
    }
}
