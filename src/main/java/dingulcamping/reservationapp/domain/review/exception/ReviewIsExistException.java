package dingulcamping.reservationapp.domain.review.exception;

import dingulcamping.reservationapp.global.exception.CustomException;
import dingulcamping.reservationapp.global.exception.ErrorCode;

public class ReviewIsExistException extends CustomException {
    
    public ReviewIsExistException(){
        super(ErrorCode.REVIEW_IS_EXIST);
    }
}
