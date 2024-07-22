package zerobase.hhs.reservation.dto.response.reserve;

import lombok.Getter;
import zerobase.hhs.reservation.type.ResponseType;

import java.time.LocalDateTime;

@Getter
public class ReserveCheckOutResponse extends ReserveResponse{

    private final LocalDateTime checkOutTime;

    public ReserveCheckOutResponse(ResponseType responseType, LocalDateTime checkOutTime) {
        super(responseType);
        this.checkOutTime = checkOutTime;
    }
}
