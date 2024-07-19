package zerobase.hhs.reservation.service;

import zerobase.hhs.reservation.domain.User;
import zerobase.hhs.reservation.dto.request.user.UserLoginRequest;
import zerobase.hhs.reservation.dto.request.user.UserRegisterRequest;
import zerobase.hhs.reservation.dto.response.user.UserLoginResponse;
import zerobase.hhs.reservation.dto.response.user.UserRegisterResponse;

public interface UserService {

    UserRegisterResponse register(UserRegisterRequest request);

    UserLoginResponse login(UserLoginRequest request);

    User getUser(Long id);
}
