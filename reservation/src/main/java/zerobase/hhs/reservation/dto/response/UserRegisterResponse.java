package zerobase.hhs.reservation.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import zerobase.hhs.reservation.Type.ResponseType;

@Getter
public class UserRegisterResponse {

    private final HttpStatus status;

    private final String message;

    public UserRegisterResponse(ResponseType responseType){
        this.status = responseType.getStatus();
        this.message = responseType.getMessage();
    }
}
