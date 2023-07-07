package dingulcamping.reservationapp.domain.member.exception;

public class RedisPwKeySaveException extends RuntimeException {
    public RedisPwKeySaveException() {
        super();
    }

    public RedisPwKeySaveException(String message) {
        super(message);
    }

    public RedisPwKeySaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisPwKeySaveException(Throwable cause) {
        super(cause);
    }
}
