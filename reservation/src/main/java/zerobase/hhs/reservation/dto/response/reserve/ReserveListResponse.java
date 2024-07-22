package zerobase.hhs.reservation.dto.response.reserve;

import lombok.Getter;
import zerobase.hhs.reservation.domain.model.ReserveDetail;
import zerobase.hhs.reservation.type.ResponseType;

import java.util.List;

@Getter
public class ReserveListResponse extends ReserveResponse {

    private final List<ReserveDetail> reserveList;

    public ReserveListResponse(ResponseType responseType, List<ReserveDetail> reserveList) {
        super(responseType);
        this.reserveList = reserveList;
    }
}
