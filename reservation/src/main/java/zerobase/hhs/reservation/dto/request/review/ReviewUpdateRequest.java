package zerobase.hhs.reservation.dto.request.review;

import lombok.Getter;


@Getter
public class ReviewUpdateRequest extends ReviewRequest{

    private final Long reviewId;

    public ReviewUpdateRequest(int fulfillment, String content , Long reservationId, Long reviewId) {
        super(fulfillment, content, reservationId);
        this.reviewId = reviewId;
    }
}
