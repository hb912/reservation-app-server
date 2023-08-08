package dingulcamping.reservationapp.domain.room.exception;

import dingulcamping.reservationapp.global.exception.CustomException;
import dingulcamping.reservationapp.global.exception.ErrorCode;

public class ExistSameNameRoomException extends CustomException {
    public ExistSameNameRoomException() {
        super(ErrorCode.SAME_NAME_ROOM_EXIST);
    }
}
