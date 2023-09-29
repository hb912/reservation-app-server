package dingulcamping.reservationapp.domain.member.exception;

import dingulcamping.reservationapp.global.exception.ErrorCode;
import dingulcamping.reservationapp.global.exception.CustomException;

public class RedisPwKeySaveException extends CustomException {
    public RedisPwKeySaveException() {
        super(ErrorCode.REDIS_PW_KEY_SAVE);
    }
}
