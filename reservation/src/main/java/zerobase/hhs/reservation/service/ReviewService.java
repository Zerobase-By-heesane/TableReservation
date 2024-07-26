package zerobase.hhs.reservation.service;

import zerobase.hhs.reservation.dto.request.review.ReviewDeleteRequest;
import zerobase.hhs.reservation.dto.request.review.ReviewRequest;
import zerobase.hhs.reservation.dto.request.review.ReviewUpdateRequest;
import zerobase.hhs.reservation.dto.response.review.ReviewDeleteResponse;
import zerobase.hhs.reservation.dto.response.review.ReviewListResponse;
import zerobase.hhs.reservation.dto.response.review.ReviewResponse;
import zerobase.hhs.reservation.dto.response.review.ReviewUpdateResponse;

public interface ReviewService {

    // 리뷰 등록
    ReviewResponse review(ReviewRequest request);

    // 특정 가게의 모든 리뷰 조회
    ReviewListResponse getReviewsByStore(Long storeId);

    // 특정 유저의 모든 리뷰 조회
    ReviewListResponse getReviewsByUser(Long userId);

    // 리뷰 수정
    ReviewUpdateResponse updateReview(ReviewUpdateRequest request);

    // 리뷰 삭제
    ReviewDeleteResponse deleteReview(ReviewDeleteRequest request);

}
