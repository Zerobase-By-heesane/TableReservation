package zerobase.hhs.reservation.dto.response.user;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import zerobase.hhs.reservation.type.ResponseType;

@Getter
public class UserRegisterResponse {

    private final HttpStatus status;

    private final String message;

    public UserRegisterResponse(ResponseType responseType){
        this.status = responseType.getStatus();
        this.message = responseType.getMessage();
    }
}
