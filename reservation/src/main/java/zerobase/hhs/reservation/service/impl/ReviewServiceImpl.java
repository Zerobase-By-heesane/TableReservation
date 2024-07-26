package zerobase.hhs.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.hhs.reservation.domain.Reservation;
import zerobase.hhs.reservation.domain.Review;
import zerobase.hhs.reservation.domain.Store;
import zerobase.hhs.reservation.domain.User;
import zerobase.hhs.reservation.dto.request.review.ReviewDeleteRequest;
import zerobase.hhs.reservation.dto.request.review.ReviewRequest;
import zerobase.hhs.reservation.dto.request.review.ReviewUpdateRequest;
import zerobase.hhs.reservation.dto.response.review.ReviewDeleteResponse;
import zerobase.hhs.reservation.dto.response.review.ReviewListResponse;
import zerobase.hhs.reservation.dto.response.review.ReviewResponse;
import zerobase.hhs.reservation.dto.response.review.ReviewUpdateResponse;
import zerobase.hhs.reservation.exception.ExceptionsType;
import zerobase.hhs.reservation.exception.exceptions.CannotFindStore;
import zerobase.hhs.reservation.repository.ReservationRepository;
import zerobase.hhs.reservation.repository.ReviewRepository;
import zerobase.hhs.reservation.repository.StoreRepository;
import zerobase.hhs.reservation.repository.UserRepository;
import zerobase.hhs.reservation.service.ReviewService;
import zerobase.hhs.reservation.type.ResponseType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    /**
     * 리뷰 등록
     * @param request 만족도와 리뷰 내용, 예약 고유 ID값
     * @return 리뷰 등록 결과
     */
    @Override
    public ReviewResponse review(ReviewRequest request) {

        try {
            Long reservationId = request.getReservationId();

            Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                    () -> new CannotFindStore(ExceptionsType.CANNOT_FIND_STORE)
            );

            Review review = Review.builder()
                    .fulfillment(request.getFulfillment())
                    .content(request.getContent())
                    .build();

            reviewRepository.save(review);
        } catch (CannotFindStore e) {
            return new ReviewResponse(ResponseType.REVIEW_FAIL, 0, "");
        }

        return new ReviewResponse(ResponseType.REVIEW_SUCCESS, request.getFulfillment(), request.getContent());
    }

    /**
     * 특정 가게에 등록된 모든 리뷰 조회
     * @param storeId 가게의 고유 ID값
     * @return 특정 가게의 모든 리뷰 리스트 반환
     */
    @Override
    public ReviewListResponse getReviewsByStore(Long storeId) {

        // 가게 ID로 가게 정보 조회
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new CannotFindStore(ExceptionsType.CANNOT_FIND_STORE)
        );

        List<ReviewResponse> reviewListByStore = store.getReservations().stream()
                .map(i -> new ReviewResponse(ResponseType.REVIEW_SUCCESS, i.getReview().getFulfillment(), i.getReview().getContent()))
                .toList();


        return new ReviewListResponse(reviewListByStore);
    }

    @Override
    public ReviewListResponse getReviewsByUser(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new CannotFindStore(ExceptionsType.CANNOT_FIND_STORE)
        );

        List<ReviewResponse> reviewListByUser = user.getReservations().stream()
                .map(i -> new ReviewResponse(ResponseType.REVIEW_SUCCESS, i.getReview().getFulfillment(), i.getReview().getContent()))
                .toList();

        return new ReviewListResponse(reviewListByUser);
    }


    /**
     * 리뷰 수정
     * @param request 리뷰 고유 ID값, 수정할 만족도와 리뷰 내용 / extends ReviewRequest
     * @return 리뷰 수정 결과
     */
    @Override
    public ReviewUpdateResponse updateReview(ReviewUpdateRequest request) {

        try {
            Long reviewId = request.getReviewId();

            Review review = reviewRepository.findById(reviewId).orElseThrow(
                    () -> new CannotFindStore(ExceptionsType.CANNOT_FIND_STORE)
            );

            review.updateReview(request.getFulfillment(), request.getContent());

            reviewRepository.save(review);
        } catch (Exception e) {
            return new ReviewUpdateResponse(ResponseType.REVIEW_UPDATE_FAIL);
        }
        return new ReviewUpdateResponse(ResponseType.REVIEW_UPDATE_SUCCESS);
    }

    /**
     * 리뷰 삭제
     * @param request 리뷰 고유 ID값
     * @return 리뷰 삭제 결과
     */
    @Override
    public ReviewDeleteResponse deleteReview(ReviewDeleteRequest request) {

        try {
            Long reviewId = request.getReviewId();

            Review review = reviewRepository.findById(reviewId).orElseThrow(
                    () -> new CannotFindStore(ExceptionsType.CANNOT_FIND_STORE)
            );

            reviewRepository.delete(review);
        } catch (Exception e) {
            return new ReviewDeleteResponse(ResponseType.REVIEW_DELETE_FAIL);
        }
        return new ReviewDeleteResponse(ResponseType.REVIEW_DELETE_SUCCESS);
    }
}
