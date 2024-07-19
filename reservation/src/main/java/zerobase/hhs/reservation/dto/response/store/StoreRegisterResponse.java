package zerobase.hhs.reservation.dto.response.store;

import lombok.Getter;
import zerobase.hhs.reservation.dto.response.SimpleResponse;
import zerobase.hhs.reservation.type.ResponseType;

@Getter
public class StoreRegisterResponse extends SimpleResponse {
    public StoreRegisterResponse(ResponseType responseType) {
        super(responseType);
    }
}
