package zerobase.hhs.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import zerobase.hhs.reservation.domain.User;
import zerobase.hhs.reservation.exception.ExceptionsType;
import zerobase.hhs.reservation.exception.exceptions.CannotFindUser;
import zerobase.hhs.reservation.repository.UserRepository;
import zerobase.hhs.reservation.service.RefreshService;
import zerobase.hhs.reservation.type.RedisKeyType;

@Service
@RequiredArgsConstructor
public class RefreshServiceImpl implements RefreshService {

    private final UserRepository userRepository;

    @Override
    @Cacheable(value = RedisKeyType.REFRESH, key = "#userId")
    public String cacheRefreshToken(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CannotFindUser(ExceptionsType.CANNOT_FIND_USER)
        );
        return user.getRefresh();
    }
}
