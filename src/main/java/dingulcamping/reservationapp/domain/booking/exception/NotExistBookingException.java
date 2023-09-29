package dingulcamping.reservationapp.domain.booking.exception;

import dingulcamping.reservationapp.global.exception.ErrorCode;
import dingulcamping.reservationapp.global.exception.CustomException;

public class NotExistBookingException extends CustomException {
    public NotExistBookingException() {
        super(ErrorCode.NOT_EXIST_BOOKING);
    }
}
