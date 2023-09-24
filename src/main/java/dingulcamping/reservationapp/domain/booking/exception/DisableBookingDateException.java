package dingulcamping.reservationapp.domain.booking.exception;

import dingulcamping.reservationapp.global.exception.ErrorCode;
import dingulcamping.reservationapp.global.exception.CustomException;

public class DisableBookingDateException extends CustomException {
    public DisableBookingDateException() {
        super(ErrorCode.DISABLE_BOOKING_DATE);
    }
}
