package dingulcamping.reservationapp.domain.member.exception;

import dingulcamping.reservationapp.global.exception.ErrorCode;
import dingulcamping.reservationapp.global.exception.CustomException;

public class RedisKeyExpiredException extends CustomException {
    public RedisKeyExpiredException() {
        super(ErrorCode.REDISKEY_EXPIRED);
    }
}
