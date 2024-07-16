package zerobase.hhs.reservation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import zerobase.hhs.reservation.Type.UserRole;

@Getter
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_name")
    private String name;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="refresh")
    private String refresh;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
