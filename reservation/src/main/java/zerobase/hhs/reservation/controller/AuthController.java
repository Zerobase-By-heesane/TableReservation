package zerobase.hhs.reservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import zerobase.hhs.reservation.dto.request.user.UserLoginRequest;
import zerobase.hhs.reservation.dto.request.user.UserRegisterRequest;
import zerobase.hhs.reservation.dto.response.user.UserLoginResponse;
import zerobase.hhs.reservation.dto.response.user.UserRegisterResponse;
import zerobase.hhs.reservation.service.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@RequestBody @Valid UserRegisterRequest request) {
        log.info("request: {}", request);
        return ResponseEntity.ok(userService.register(request));

    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest request){
        return ResponseEntity.ok(userService.login(request));
    }
}
