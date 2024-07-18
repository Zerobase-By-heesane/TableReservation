package zerobase.hhs.reservation.service;

import zerobase.hhs.reservation.Type.UserRole;
import zerobase.hhs.reservation.dto.request.UserLoginRequest;
import zerobase.hhs.reservation.dto.request.UserRegisterRequest;
import zerobase.hhs.reservation.dto.response.UserLoginResponse;
import zerobase.hhs.reservation.dto.response.UserRegisterResponse;

public interface UserService {

    UserRegisterResponse register(UserRegisterRequest request);

    UserLoginResponse login(UserLoginRequest request);

}
