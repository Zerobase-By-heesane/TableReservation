package zerobase.hhs.reservation.dto.response.reserve;

import lombok.Getter;
import zerobase.hhs.reservation.dto.response.SimpleResponse;
import zerobase.hhs.reservation.type.ResponseType;

@Getter
public class ReserveResponse extends SimpleResponse {
    public ReserveResponse(ResponseType responseType) {
        super(responseType);
    }
}
