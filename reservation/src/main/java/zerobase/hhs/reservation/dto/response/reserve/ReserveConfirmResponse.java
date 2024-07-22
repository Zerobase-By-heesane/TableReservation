package zerobase.hhs.reservation.dto.response.reserve;

import lombok.Getter;
import zerobase.hhs.reservation.type.ResponseType;

@Getter
public class ReserveConfirmResponse extends ReserveResponse {
    public ReserveConfirmResponse(ResponseType responseType) {
        super(responseType);
    }
}
