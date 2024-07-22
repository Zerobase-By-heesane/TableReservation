package zerobase.hhs.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.hhs.reservation.domain.Reservation;
import zerobase.hhs.reservation.domain.Store;
import zerobase.hhs.reservation.domain.User;
import zerobase.hhs.reservation.domain.model.ReserveDetail;
import zerobase.hhs.reservation.dto.request.reserve.*;
import zerobase.hhs.reservation.dto.response.reserve.*;
import zerobase.hhs.reservation.repository.ReservationRepository;
import zerobase.hhs.reservation.repository.StoreRepository;
import zerobase.hhs.reservation.repository.UserRepository;
import zerobase.hhs.reservation.service.ReservationService;
import zerobase.hhs.reservation.type.ApprovedType;
import zerobase.hhs.reservation.type.ReserveType;
import zerobase.hhs.reservation.type.ResponseType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Override
    public ReserveListResponse getMyReservationList(ReserveListRequest request) {
        Long userId = request.getUserId();

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );

        List<Reservation> allReservationByUser = user.getReservations();

        return new ReserveListResponse(
                ResponseType.STORE_RESERVE_LIST_SUCCESS,
                allReservationByUser.stream()
                    .map(ReserveDetail::of).toList()
        );
    }

    // 예약 신청
    @Transactional
    @Override
    public ReserveResponse reserve(ReserveRegisterRequest request) {

        // 예약하고자 하는 가게의 Id
        Long storeId = request.getStoreId();

        // 예약하려는 사람의 Id
        Long userId = request.getUserId();

        // 요청 정보 찾기
        User requestUser = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );

        Store requestStore = storeRepository.findById(storeId).orElseThrow(
                () -> new IllegalArgumentException("해당 가게가 존재하지 않습니다.")
        );

        //TODO : 수용인원과 예약인원 비교 후 예약 가능 여부 확인

        try {

            log.info("approved Type : {}", ApprovedType.NOT_YET);

            // 우선 일단 그냥 구현
            Reservation reservation = Reservation.builder()
                    .reserveTime(request.getReserveTime())
                    .checkInTime(null)
                    .people(request.getPeople())
                    .checkStatus(ReserveType.NOT_YET)
                    .approvedStatus(ApprovedType.NOT_YET)
                    .store(requestStore)
                    .user(requestUser)
                    .build();
            log.info("reservation : {}", reservation.toString());
            Reservation save = reservationRepository.save(reservation);
            log.warn("save : {}", save);
        } catch (Exception e) {
            log.error("error : {}", e.getMessage());
            return new ReserveResponse(ResponseType.STORE_RESERVE_FAIL);
        }
        return new ReserveResponse(ResponseType.STORE_RESERVE_SUCCESS);
    }

    // 예약 취소
    @Transactional
    @Override
    public ReserveCancelResponse cancel(ReserveCancelRequest request) {

        // 예약의 고유 id
        Long reserveId = request.getReserveId();

        // 예약하려는 사람의 Id
        Long userId = request.getUserId();

        // 예약 정보 찾기
        Reservation reservation = reservationRepository.findById(reserveId).orElseThrow(
                () -> new IllegalArgumentException("해당 예약이 존재하지 않습니다.")
        );

        // 예약한 사람과 취소하려는 사람이 같은지 확인
        if (!Objects.equals(reservation.getUser().getId(), userId)) {
            return new ReserveCancelResponse(ResponseType.STORE_RESERVE_CANCEL_FAIL);
        }
        return new ReserveCancelResponse(ResponseType.STORE_RESERVE_CANCEL_SUCCESS);
    }

    /**
     * 예약 체크인
     * @param request 예약 체크인 요청
     * @return 성공, 실패 여부
     */
    @Transactional
    @Override
    public ReserveCheckInResponse checkIn(ReserveCheckInRequest request) {
        // 예약의 고유 id
        Long reserveId = request.getReserveId();

        // 예약하려는 사람의 Id
        Long userId = request.getUserId();

        // 예약 정보 찾기
        Reservation reservation = reservationRepository.findById(reserveId).orElseThrow(
                () -> new IllegalArgumentException("해당 예약이 존재하지 않습니다.")
        );

        log.info("reservation user id : {}", reservation.getUser().getId());
        log.info("request user id : {}", userId);

        // 예약한 사람과 취소하려는 사람이 같은지 확인
        if (!Objects.equals(reservation.getUser().getId(), userId)) {
            return new ReserveCheckInResponse(ResponseType.STORE_RESERVE_CHECKIN_FAIL, null);
        }

        // 예약 승인 여부 검사
        if(reservation.getApprovedStatus() != ApprovedType.APPROVED){
            return new ReserveCheckInResponse(ResponseType.STORE_RESERVE_CANCEL_FAIL, null);
        }

        // 체크인 시간 업데이트
        LocalDateTime now = LocalDateTime.now();

        reservation.updateCheckInTime(now);
        reservation.updateCheckStatus(ReserveType.CHECK_IN);
        log.info("reservation check in time : {}", reservation.getCheckInTime());
        return new ReserveCheckInResponse(ResponseType.STORE_RESERVE_CHECKIN_SUCCESS,now);
    }

    // 체크 아웃
    @Transactional
    @Override
    public ReserveCheckOutResponse checkOut(ReserveCheckOutRequest request) {

        Long reservedId = request.getReserveId();

        Long userId = request.getUserId();

        Reservation reservation = reservationRepository.findById(reservedId).orElseThrow(
                () -> new IllegalArgumentException("해당 예약이 존재하지 않습니다.")
        );

        if (!Objects.equals(reservation.getUser().getId(), userId)) {
            return new ReserveCheckOutResponse(ResponseType.STORE_RESERVE_CHECKOUT_FAIL, null);
        }

        // 체크인 상태 확인
        if(reservation.getCheckStatus() != ReserveType.CHECK_IN){
            return new ReserveCheckOutResponse(ResponseType.STORE_RESERVE_CHECKOUT_FAIL, null);
        }

        reservation.updateCheckStatus(ReserveType.CHECK_OUT);


        return new ReserveCheckOutResponse(ResponseType.STORE_RESERVE_CHECKOUT_SUCCESS, LocalDateTime.now());
    }

    // 해당 가계의 예약 내역 리스트를 가져오는 메서드
    @Transactional
    @Override
    public ReserveListResponse getReservationList(ReserveRequest request) {

        log.info("request : {}", request.toString());
        Long storeId = request.getStoreId();

        Long userId = request.getUserId();

        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new IllegalArgumentException("해당 가게가 존재하지 않습니다.")
        );

        if (!Objects.equals(store.getManager().getId(), userId)) {
            return new ReserveListResponse(ResponseType.STORE_RESERVE_LIST_FAIL, null);
        }
        List<Reservation> allByStoreAndApprovedStatus = reservationRepository.findAllByStoreAndApprovedStatus(store, ApprovedType.NOT_YET);

        List<ReserveDetail> list = allByStoreAndApprovedStatus.stream()
                .map(ReserveDetail::of).toList();

        return new ReserveListResponse(ResponseType.STORE_RESERVE_LIST_SUCCESS, list);
    }

    // 예약 승인
    @Transactional
    @Override
    public ReserveConfirmResponse approveReservation(ReserveConfirmRequest request) {

        Long reserveId = request.getReserveId();

        Long partnerUserId = request.getUserId();

        Long storeId = request.getStoreId();

        Reservation reservation = reservationRepository.findById(reserveId).orElseThrow(
                () -> new IllegalArgumentException("해당 예약이 존재하지 않습니다.")
        );

        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new IllegalArgumentException("해당 가게가 존재하지 않습니다.")
        );

        log.info("store's manager user id : {}", store.getManager().getId());
        log.info("partner user id : {}", partnerUserId);

        // 가게의 매니저와 요청한 유저가 같은지 확인
        if (!Objects.equals(store.getManager().getId(), partnerUserId)) {
            return new ReserveConfirmResponse(ResponseType.STORE_RESERVE_CONFIRM_FAIL);
        }

        // 승인
        reservation.updateApprovedStatus(ApprovedType.APPROVED);

        return new ReserveConfirmResponse(ResponseType.STORE_RESERVE_CONFIRM_SUCCESS);
    }

    // 예약 거부
    @Transactional
    @Override
    public ReserveDenyResponse rejectReservation(ReserveDenyRequest request) {

        Long reserveId = request.getReserveId();

        Long partnerUserId = request.getUserId();

        Long storeId = request.getStoreId();

        Reservation reservation = reservationRepository.findById(reserveId).orElseThrow(
                () -> new IllegalArgumentException("해당 예약이 존재하지 않습니다.")
        );

        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new IllegalArgumentException("해당 가게가 존재하지 않습니다.")
        );

        // 가게의 매니저와 요청한 유저가 같은지 확인
        if (!Objects.equals(store.getManager().getId(), partnerUserId)) {
            return new ReserveDenyResponse(ResponseType.STORE_RESERVE_CHECKIN_FAIL);
        }

        // 승인
        reservation.updateApprovedStatus(ApprovedType.APPROVED);

        return new ReserveDenyResponse(ResponseType.STORE_RESERVE_CHECKIN_SUCCESS);
    }
}
