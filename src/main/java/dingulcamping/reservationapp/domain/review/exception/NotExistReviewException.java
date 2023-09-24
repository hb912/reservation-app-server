package dingulcamping.reservationapp.domain.review.exception;

import dingulcamping.reservationapp.global.exception.CustomException;
import dingulcamping.reservationapp.global.exception.ErrorCode;

public class NotExistReviewException extends CustomException {
    public NotExistReviewException() {
        super(ErrorCode.NOT_EXIST_REVIEW);
    }
}
