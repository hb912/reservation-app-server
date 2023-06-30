package dingulcamping.reservationapp.domain.member.exception;

public class MemberIsNotExistException extends RuntimeException {
    public MemberIsNotExistException() {
        super();
    }

    public MemberIsNotExistException(String message) {
        super(message);
    }

    public MemberIsNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberIsNotExistException(Throwable cause) {
        super(cause);
    }
}
