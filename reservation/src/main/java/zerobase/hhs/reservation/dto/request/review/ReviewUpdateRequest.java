package zerobase.hhs.reservation.dto.request.review;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewUpdateRequest extends ReviewRequest{

    private Long reviewId;

    public ReviewUpdateRequest(ReviewRequest reviewRequest, Long reviewId) {
        super(reviewRequest.getFulfillment(), reviewRequest.getContent(), reviewRequest.getReservationId());
        this.reviewId = reviewId;
    }
}
