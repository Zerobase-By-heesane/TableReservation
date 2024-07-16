package zerobase.hhs.reservation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.hhs.reservation.Type.UserRole;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    @Email
    private String email;

    private UserRole role;

    public void update(String hashPassword){
        this.password = hashPassword;
    }
}
