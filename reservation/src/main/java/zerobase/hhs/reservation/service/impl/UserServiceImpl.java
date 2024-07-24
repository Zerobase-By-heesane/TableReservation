package zerobase.hhs.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import zerobase.hhs.reservation.domain.User;
import zerobase.hhs.reservation.dto.JwtToken;
import zerobase.hhs.reservation.dto.request.user.UserLoginRequest;
import zerobase.hhs.reservation.dto.request.user.UserRegisterRequest;
import zerobase.hhs.reservation.dto.response.user.UserLoginResponse;
import zerobase.hhs.reservation.dto.response.user.UserRegisterResponse;
import zerobase.hhs.reservation.exception.ExceptionsType;
import zerobase.hhs.reservation.exception.exceptions.AlreadyExistEmail;
import zerobase.hhs.reservation.exception.exceptions.CannotFindUser;
import zerobase.hhs.reservation.exception.exceptions.CannotFindUserByEmail;
import zerobase.hhs.reservation.exception.exceptions.DontMatchPassword;
import zerobase.hhs.reservation.jwt.JwtTokenUtil;
import zerobase.hhs.reservation.repository.UserRepository;
import zerobase.hhs.reservation.service.UserService;
import zerobase.hhs.reservation.type.ResponseType;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * 회원가입
     * @param request 회원가입 정보 요청
     *                - name : 이름
     *                - password : 비밀번호
     *                - email : 이메일
     *                - role : 권한 (default : USER)
     * @return 회원가입 결과 응답
     */
    @Override
    public UserRegisterResponse register(UserRegisterRequest request) {

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new AlreadyExistEmail(ExceptionsType.ALREADY_EXIST_EMAIL);
        }

        String rawPassword = request.getPassword();

        request.update(passwordEncoder.encode(rawPassword));

        userRepository.save(UserRegisterRequest.of(request));

        return new UserRegisterResponse(ResponseType.REGISTER_SUCCESS);
    }

    /**
     * 로그인
     * @param request 로그인 정보 요청
     *                - email : 이메일
     *                - password : 비밀번호
     * @return 로그인 결과 응답
     *                - jwtToken : jwt 토큰
     */
    @Override
    public UserLoginResponse login(UserLoginRequest request){


        // 유저가 존재하지 않으면,
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new CannotFindUserByEmail(ExceptionsType.CANNOT_FIND_USER_BY_EMAIL)
        );

        String rawPassword = request.getPassword();

        // 비밀번호가 다르면,
        if(!passwordEncoder.matches(rawPassword, user.getPassword())){
            throw new DontMatchPassword(ExceptionsType.DONT_MATCH_PASSWORD);
        }

        JwtToken jwtToken = jwtTokenUtil.generateToken(user);

        return new UserLoginResponse(jwtToken);
    }

    /**
     * 유저 정보 조회
     * @param id 유저의 고유 id 값
     * @return id에 해당하는 유저 정보
     */
    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new CannotFindUser(ExceptionsType.CANNOT_FIND_USER)
        );
    }
}
