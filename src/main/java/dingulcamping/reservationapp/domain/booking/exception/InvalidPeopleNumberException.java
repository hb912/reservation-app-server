package dingulcamping.reservationapp.domain.booking.exception;

import dingulcamping.reservationapp.global.exception.ErrorCode;
import dingulcamping.reservationapp.global.exception.CustomException;

public class InvalidPeopleNumberException extends CustomException {
    public InvalidPeopleNumberException() {
        super(ErrorCode.INVALID_PEOPLE_NUMBER);
    }
}
