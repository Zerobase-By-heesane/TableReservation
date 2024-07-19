package zerobase.hhs.reservation.dto.response.store;

import lombok.Getter;
import zerobase.hhs.reservation.dto.response.SimpleResponse;
import zerobase.hhs.reservation.type.ResponseType;

@Getter
public class StoreUpdateResponse extends SimpleResponse {
    public StoreUpdateResponse(ResponseType responseType) {
        super(responseType);
    }
}
