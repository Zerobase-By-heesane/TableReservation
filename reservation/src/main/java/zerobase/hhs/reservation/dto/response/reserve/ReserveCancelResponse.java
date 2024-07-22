package zerobase.hhs.reservation.dto.response.reserve;

import lombok.Getter;
import zerobase.hhs.reservation.type.ResponseType;

@Getter
public class ReserveCancelResponse extends ReserveResponse {
    public ReserveCancelResponse(ResponseType responseType) {
        super(responseType);
    }
}
