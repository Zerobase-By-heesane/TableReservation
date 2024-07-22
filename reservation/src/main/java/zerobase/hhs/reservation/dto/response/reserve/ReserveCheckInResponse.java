package zerobase.hhs.reservation.dto.response.reserve;

import lombok.Getter;
import zerobase.hhs.reservation.type.ResponseType;

import java.time.LocalDateTime;

@Getter
public class ReserveCheckInResponse extends ReserveResponse {
    private final LocalDateTime checkInTime;

    public ReserveCheckInResponse(ResponseType responseType, LocalDateTime checkInTime) {
        super(responseType);
        this.checkInTime = checkInTime;

    }
}
