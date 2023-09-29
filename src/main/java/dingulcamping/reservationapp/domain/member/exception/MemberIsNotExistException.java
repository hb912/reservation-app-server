package dingulcamping.reservationapp.domain.member.exception;

import dingulcamping.reservationapp.global.exception.ErrorCode;
import dingulcamping.reservationapp.global.exception.CustomException;

public class MemberIsNotExistException extends CustomException {

    public MemberIsNotExistException() {
        super(ErrorCode.MEMBER_IS_NOT_EXIST);
    }
}
