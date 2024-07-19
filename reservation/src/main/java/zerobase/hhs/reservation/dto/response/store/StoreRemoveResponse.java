package zerobase.hhs.reservation.dto.response.store;

import lombok.Getter;
import zerobase.hhs.reservation.dto.response.SimpleResponse;
import zerobase.hhs.reservation.type.ResponseType;

@Getter
public class StoreRemoveResponse extends SimpleResponse {
    public StoreRemoveResponse(ResponseType responseType) {
        super(responseType);
    }
}
