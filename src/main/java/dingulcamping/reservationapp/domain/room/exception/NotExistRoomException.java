package dingulcamping.reservationapp.domain.room.exception;

import dingulcamping.reservationapp.global.exception.CustomException;
import dingulcamping.reservationapp.global.exception.ErrorCode;

public class NotExistRoomException extends CustomException {
    public NotExistRoomException() {
        super(ErrorCode.ROOM_IS_NOT_EXIST);
    }
}
