package zerobase.hhs.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionsType {
    CANNOT_FIND_USER_BY_EMAIL(HttpStatus.BAD_REQUEST,"AU001","해당 이메일을 가지는 유저를 찾을 수 없습니다."),
    ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST,"AU002","이미 존재하는 이메일입니다."),
    DONT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST,"AU003","비밀번호가 일치하지 않습니다."),
    CANNOT_FIND_USER(HttpStatus.BAD_REQUEST,"AU004","해당 유저를 찾을 수 없습니다."),
    CANNOT_FIND_STORE(HttpStatus.BAD_REQUEST,"AU005","해당 가게를 찾을 수 없습니다."),
    CANNOT_FIND_RESERVATION(HttpStatus.BAD_REQUEST,"AU006","해당 예약을 찾을 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
