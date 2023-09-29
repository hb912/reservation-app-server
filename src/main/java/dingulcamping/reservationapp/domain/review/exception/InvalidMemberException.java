package dingulcamping.reservationapp.domain.review.exception;

import dingulcamping.reservationapp.global.exception.CustomException;
import dingulcamping.reservationapp.global.exception.ErrorCode;

public class InvalidMemberException extends CustomException {
    public InvalidMemberException() {
        super(ErrorCode.INVALID_MEMBER);
    }
}
