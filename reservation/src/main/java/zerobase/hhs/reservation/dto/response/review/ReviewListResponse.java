package zerobase.hhs.reservation.dto.response.review;

import lombok.Getter;
import zerobase.hhs.reservation.domain.model.ReviewDetail;

import java.util.List;

@Getter
public class ReviewListResponse {
    private final List<ReviewDetail> reviewList;

    public ReviewListResponse(List<ReviewResponse> reviewList) {
        this.reviewList = reviewList.stream()
                .map(i -> new ReviewDetail(i.getFulfillment(), i.getContent()))
                .toList();
    }
}
