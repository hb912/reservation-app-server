package dingulcamping.reservationapp.domain.member.exception;

public class RedisKeyExpiredException extends RuntimeException {
    public RedisKeyExpiredException() {
        super();
    }

    public RedisKeyExpiredException(String message) {
        super(message);
    }

    public RedisKeyExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisKeyExpiredException(Throwable cause) {
        super(cause);
    }
}
