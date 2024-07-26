package zerobase.hhs.reservation.dto.response.review;

import lombok.Getter;
import zerobase.hhs.reservation.dto.response.SimpleResponse;
import zerobase.hhs.reservation.type.ResponseType;

@Getter
public class ReviewResponse extends SimpleResponse {

    Integer fulfillment;
    String content;

    public ReviewResponse(ResponseType responseType, Integer fulfillment, String content) {
        super(responseType);
        this.fulfillment = fulfillment;
        this.content = content;
    }
}
