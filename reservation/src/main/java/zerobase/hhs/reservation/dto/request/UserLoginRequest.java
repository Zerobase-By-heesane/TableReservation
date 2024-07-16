package zerobase.hhs.reservation.dto.request;

import jakarta.validation.constraints.NotNull;

public class UserLoginRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
