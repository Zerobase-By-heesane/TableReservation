package zerobase.hhs.reservation.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseType {
    REGISTER_SUCCESS(HttpStatus.CREATED, "회원가입 성공"),
    REGISTER_FAIL(HttpStatus.BAD_REQUEST, "회원가입 실패"),

    LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공"),
    LOGIN_FAIL(HttpStatus.BAD_REQUEST, "로그인 실패"),

    STORE_REGISTER_SUCCESS(HttpStatus.CREATED, "가게 등록 성공"),
    STORE_REGISTER_FAIL(HttpStatus.BAD_REQUEST, "가게 등록 실패"),

    STORE_EDIT_SUCCESS(HttpStatus.OK, "가게 수정 성공"),
    STORE_EDIT_FAIL(HttpStatus.BAD_REQUEST, "가게 수정 실패"),

    STORE_DELETE_SUCCESS(HttpStatus.OK, "가게 삭제 성공"),
    STORE_DELETE_FAIL(HttpStatus.BAD_REQUEST, "가게 삭제 실패"),

    STORE_RESERVE_SUCCESS(HttpStatus.OK, "예약 성공"),
    STORE_RESERVE_FAIL(HttpStatus.BAD_REQUEST, "예약 실패");

    private final HttpStatus status;
    private final String message;
}