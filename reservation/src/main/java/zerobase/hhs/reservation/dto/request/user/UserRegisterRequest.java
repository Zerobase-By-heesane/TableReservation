package zerobase.hhs.reservation.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import zerobase.hhs.reservation.domain.User;
import zerobase.hhs.reservation.type.UserRole;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserRegisterRequest {

    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    @Email
    private String email;

    private List<UserRole> role;

    public void update(String hashPassword){
        this.password = hashPassword;
    }

    public static User of(UserRegisterRequest request){
        return User.builder()
                .name(request.getName())
                .password(request.getPassword())
                .email(request.getEmail())
                .userRole(request.getRole())
                .build();
    }
}
