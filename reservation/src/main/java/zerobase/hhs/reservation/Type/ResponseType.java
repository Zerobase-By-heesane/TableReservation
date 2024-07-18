package zerobase.hhs.reservation.Type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseType {
    CREATE_SUCCESS(HttpStatus.CREATED, "회원가입 성공"),
    CREATE_FAIL(HttpStatus.BAD_REQUEST, "회원가입 실패");

    private final HttpStatus status;
    private final String message;
}
