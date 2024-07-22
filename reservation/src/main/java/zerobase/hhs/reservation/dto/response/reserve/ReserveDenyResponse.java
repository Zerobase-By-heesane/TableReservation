package zerobase.hhs.reservation.dto.response.reserve;

import lombok.Getter;
import zerobase.hhs.reservation.type.ResponseType;

@Getter
public class ReserveDenyResponse extends ReserveResponse {
    public ReserveDenyResponse(ResponseType responseType) {
        super(responseType);
    }
}
