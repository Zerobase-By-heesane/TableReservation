package zerobase.hhs.reservation.dto.response.review;

import lombok.Getter;
import zerobase.hhs.reservation.dto.response.SimpleResponse;
import zerobase.hhs.reservation.type.ResponseType;

@Getter
public class ReviewUpdateResponse extends SimpleResponse {
    public ReviewUpdateResponse(ResponseType responseType) {
        super(responseType);
    }
}