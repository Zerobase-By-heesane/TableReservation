package zerobase.hhs.reservation.dto.response;

import org.springframework.http.HttpStatus;

public class UserRegisterResponse {

    private HttpStatus status;

    private String message;

    public UserRegisterResponse(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }
}
