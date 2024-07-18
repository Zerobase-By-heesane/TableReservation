package zerobase.hhs.reservation.dto.response;

import lombok.Getter;
import zerobase.hhs.reservation.dto.JwtToken;

@Getter
public class UserLoginResponse {

    private final String accessToken;

    private final String refreshToken;

    public UserLoginResponse(JwtToken tokens){
        this.accessToken = tokens.accessToken();
        this.refreshToken = tokens.refreshToken();
    }
}
