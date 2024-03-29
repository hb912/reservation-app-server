package dingulcamping.reservationapp.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDto {
    private int status;
    private String reason;
    private String result;
}
