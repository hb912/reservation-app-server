package dingulcamping.reservationapp.domain.room.exception;

public class ExistSameNameRoomException extends RuntimeException {
    public ExistSameNameRoomException() {
        super();
    }

    public ExistSameNameRoomException(String message) {
        super(message);
    }

    public ExistSameNameRoomException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistSameNameRoomException(Throwable cause) {
        super(cause);
    }
}
