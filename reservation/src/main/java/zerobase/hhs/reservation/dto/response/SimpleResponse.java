package zerobase.hhs.reservation.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import zerobase.hhs.reservation.type.ResponseType;

@Getter
public class SimpleResponse {
    private final HttpStatus status;
    private final String message;

    public SimpleResponse(ResponseType responseType) {
        this.status = responseType.getStatus();
        this.message = responseType.getMessage();
    }
}
