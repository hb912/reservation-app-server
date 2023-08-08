package dingulcamping.reservationapp.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //GLOBAL
    INTERNAL_SERVER_ERROR(500,"서버 오류입니다. 관리자에게 문의하세요"),

    //Member
    EMAIL_ALREADY_EXIST(400,"이 이메일은 현재 사용중입니다. 다른 이메일을 입력해 주세요."),
    MEMBER_IS_NOT_EXIST(404,"멤버 정보가 존재하지 않습니다."),
    NAME_IS_NOT_CORRECT(400, "멤버 정보가 일치하지 않습니다."),
    PASSWORD_NOT_MATCH(400, "패스워드가 일치하지 않습니다."),
    REDISKEY_EXPIRED(400, "링크의 유효기간이 만료되었습니다."),
    REDIS_PW_KEY_SAVE(500,"서버에 문제가 있습니다."),

    //ROOM
    SAME_NAME_ROOM_EXIST(400,"같은 이름의 방이 존재합니다."),
    ROOM_IS_NOT_EXIST(404,"해당 방은 존재하지 않습니다."),

    //BOOKING
    DISABLE_BOOKING_DATE(400,"해당 기간에 이미 예약이 존재합니다."),
    INVALID_PEOPLE_NUMBER(400,"정원이 맞지 않습니다."),
    INVALID_START_DATE(400,"날짜가 유효하지 않습니다."),
    NOT_EXIST_BOOKING(404,"해당 예약을 찾을 수 없습니다.");

    //REVIEW

    private final int status;
    private final String message;

}
