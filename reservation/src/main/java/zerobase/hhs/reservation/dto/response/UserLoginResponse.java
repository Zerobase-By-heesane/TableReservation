package zerobase.hhs.reservation.dto.response;

public class UserLoginResponse {

    private final String accessToken;

    private final String refreshToken;

    public UserLoginResponse(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
