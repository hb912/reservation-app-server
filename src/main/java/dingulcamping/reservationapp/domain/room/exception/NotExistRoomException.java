package dingulcamping.reservationapp.domain.room.exception;

public class NotExistRoomException extends RuntimeException {
    public NotExistRoomException() {
        super();
    }

    public NotExistRoomException(String message) {
        super(message);
    }

    public NotExistRoomException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistRoomException(Throwable cause) {
        super(cause);
    }
}
