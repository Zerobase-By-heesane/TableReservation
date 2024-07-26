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
    STORE_RESERVE_FAIL(HttpStatus.BAD_REQUEST, "예약 실패"),
    STORE_RESERVE_FAIL_OVER_CAPACITY(HttpStatus.BAD_REQUEST, "수용인원 초과"),

    STORE_RESERVE_CANCEL_SUCCESS(HttpStatus.OK, "예약 취소 성공"),
    STORE_RESERVE_CANCEL_FAIL(HttpStatus.BAD_REQUEST, "예약 취소 실패"),

    STORE_RESERVE_CHECKIN_SUCCESS(HttpStatus.OK, "체크인 성공"),
    STORE_RESERVE_CHECKIN_FAIL(HttpStatus.BAD_REQUEST, "체크인 실패"),

    STORE_RESERVE_CONFIRM_SUCCESS(HttpStatus.OK, "예약 승인 성공"),
    STORE_RESERVE_CONFIRM_FAIL(HttpStatus.BAD_REQUEST, "예약 승인 실패"),

    STORE_RESERVE_CHECKOUT_SUCCESS(HttpStatus.OK, "체크아웃 성공"),
    STORE_RESERVE_CHECKOUT_FAIL(HttpStatus.BAD_REQUEST, "체크아웃 실패"),

    STORE_RESERVE_DENY_FAIL(HttpStatus.BAD_REQUEST, "예약 거부 실패"),
    STORE_RESERVE_DENY_SUCCESS(HttpStatus.OK, "예약 거부 성공"),

    STORE_RESERVE_LIST_SUCCESS(HttpStatus.OK, "예약 목록 조회 성공"),
    STORE_RESERVE_LIST_FAIL(HttpStatus.BAD_REQUEST, "예약 목록 조회 실패"),

    REVIEW_SUCCESS(HttpStatus.OK, "리뷰 조회 성공"),
    REVIEW_FAIL(HttpStatus.BAD_REQUEST, "리뷰 조회 실패"),

    REVIEW_UPDATE_SUCCESS(HttpStatus.OK, "리뷰 수정 성공"),
    REVIEW_UPDATE_FAIL(HttpStatus.BAD_REQUEST, "리뷰 수정 실패"),

    REVIEW_DELETE_SUCCESS(HttpStatus.OK, "리뷰 삭제 성공"),
    REVIEW_DELETE_FAIL(HttpStatus.BAD_REQUEST, "리뷰 삭제 실패")
    ;

    private final HttpStatus status;
    private final String message;
}
