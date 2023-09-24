package dingulcamping.reservationapp.domain.booking.exception;

import dingulcamping.reservationapp.global.exception.ErrorCode;
import dingulcamping.reservationapp.global.exception.CustomException;

public class InvalidStartDate extends CustomException {
    public InvalidStartDate() {
        super(ErrorCode.INVALID_START_DATE);
    }
}
