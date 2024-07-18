package zerobase.hhs.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import zerobase.hhs.reservation.Type.ResponseType;
import zerobase.hhs.reservation.domain.User;
import zerobase.hhs.reservation.dto.JwtToken;
import zerobase.hhs.reservation.dto.request.UserLoginRequest;
import zerobase.hhs.reservation.dto.request.UserRegisterRequest;
import zerobase.hhs.reservation.dto.response.UserLoginResponse;
import zerobase.hhs.reservation.dto.response.UserRegisterResponse;
import zerobase.hhs.reservation.jwt.JwtTokenUtil;
import zerobase.hhs.reservation.repository.UserRepository;
import zerobase.hhs.reservation.service.UserService;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public UserRegisterResponse register(UserRegisterRequest request) {

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        log.info("request: {}", request);
        String rawPassword = request.getPassword();
        log.info("rawPassword: {}", rawPassword);
        request.update(passwordEncoder.encode(rawPassword));

        userRepository.save(UserRegisterRequest.of(request));

        return new UserRegisterResponse(ResponseType.CREATE_SUCCESS);
    }

    @Override
    public UserLoginResponse login(UserLoginRequest request){

        log.info(request.getEmail());
        // 유저가 존재하지 않으면,
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("해당 이메일을 가진 유저가 존재하지 않습니다.")
        );

        String rawPassword = request.getPassword();

        // 비밀번호가 다르면,
        if(!passwordEncoder.matches(rawPassword, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        JwtToken jwtToken = jwtTokenUtil.generateToken(user.getId());

        return new UserLoginResponse(jwtToken);
    }
}
