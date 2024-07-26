package zerobase.hhs.reservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.hhs.reservation.dto.request.review.ReviewDeleteRequest;
import zerobase.hhs.reservation.dto.request.review.ReviewRequest;
import zerobase.hhs.reservation.dto.request.review.ReviewUpdateRequest;
import zerobase.hhs.reservation.dto.response.review.ReviewDeleteResponse;
import zerobase.hhs.reservation.dto.response.review.ReviewListResponse;
import zerobase.hhs.reservation.dto.response.review.ReviewResponse;
import zerobase.hhs.reservation.dto.response.review.ReviewUpdateResponse;
import zerobase.hhs.reservation.service.ReviewService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    // 가게별 리뷰 조회
    @GetMapping("/store/{storeId}")
    public ResponseEntity<ReviewListResponse> getReviewListByStoreId(@PathVariable Long storeId) {
        return ResponseEntity.ok(reviewService.getReviewsByStore(storeId));
    }

    // 유저별 리뷰 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<ReviewListResponse> getReviewListByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }

    // 리뷰 등록
    @PostMapping("/add")
    public ResponseEntity<ReviewResponse> addReview(@RequestBody @Valid ReviewRequest request) {
        return ResponseEntity.ok(reviewService.review(request));
    }

    // 리뷰 수정
    @PatchMapping("/update")
    public ResponseEntity<ReviewUpdateResponse> updateReview(@RequestBody @Valid ReviewUpdateRequest request) {
        return ResponseEntity.ok(reviewService.updateReview(request));
    }

    // 리뷰 삭제
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<ReviewDeleteResponse> deleteReview(@PathVariable ReviewDeleteRequest reviewId) {
        return ResponseEntity.ok(reviewService.deleteReview(reviewId));
    }
}
