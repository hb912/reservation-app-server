package dingulcamping.reservationapp.domain.member.exception;

import dingulcamping.reservationapp.global.exception.ErrorCode;
import dingulcamping.reservationapp.global.exception.CustomException;

public class EmailAlreadyExistException extends CustomException {
    public EmailAlreadyExistException() {
        super(ErrorCode.EMAIL_ALREADY_EXIST);
    }
}
