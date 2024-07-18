package zerobase.hhs.reservation.dto;

import lombok.Builder;

@Builder
public record JwtToken(
        String accessToken,
        String refreshToken) {

}