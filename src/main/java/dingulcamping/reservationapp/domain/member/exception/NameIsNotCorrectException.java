package dingulcamping.reservationapp.domain.member.exception;

import dingulcamping.reservationapp.global.exception.ErrorCode;
import dingulcamping.reservationapp.global.exception.CustomException;

public class NameIsNotCorrectException extends CustomException {
    public NameIsNotCorrectException() {
        super(ErrorCode.NAME_IS_NOT_CORRECT);
    }
}
