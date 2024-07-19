package zerobase.hhs.reservation.dto.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class UserLoginRequest {
    @NotNull
    String email;
    @NotNull
    String password;
}
