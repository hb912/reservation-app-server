package dingulcamping.reservationapp.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static dingulcamping.reservationapp.global.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomException.class})
    protected ResponseEntity handleCustomException(CustomException ex) {
        return new ResponseEntity(
                new ErrorDto(ex.getErrorCode().getStatus(), ex.getErrorCode().getMessage(), "error"),
                HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity handleServerException(Exception e) {
        log.error("error Message = {}", e.getMessage());
        return new ResponseEntity(
                new ErrorDto(INTERNAL_SERVER_ERROR.getStatus(), INTERNAL_SERVER_ERROR.getMessage(), "error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
