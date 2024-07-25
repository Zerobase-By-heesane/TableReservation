package zerobase.hhs.reservation.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.hhs.reservation.domain.Reservation;
import zerobase.hhs.reservation.domain.Store;
import zerobase.hhs.reservation.domain.User;
import zerobase.hhs.reservation.domain.model.ReserveDetail;
import zerobase.hhs.reservation.dto.request.reserve.*;
import zerobase.hhs.reservation.dto.response.reserve.*;
import zerobase.hhs.reservation.exception.ExceptionsType;
import zerobase.hhs.reservation.exception.exceptions.CannotFindReservation;
import zerobase.hhs.reservation.exception.exceptions.CannotFindStore;
import zerobase.hhs.reservation.exception.exceptions.CannotFindUser;
import zerobase.hhs.reservation.repository.ReservationRepository;
import zerobase.hhs.reservation.repository.StoreRepository;
import zerobase.hhs.reservation.repository.UserRepository;
import zerobase.hhs.reservation.service.ReservationService;
import zerobase.hhs.reservation.type.ApprovedType;
import zerobase.hhs.reservation.type.RedisKeyType;
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
    private final RedisTemplate<String,Long>  redisTemplate;

    /**
     * 내 예약 리스트 가져오기
     * @param request 내 예약 리스트 요청
     * @return 내 예약 리스트 응답
     */
    @Override
    public ReserveListResponse getMyReservationList(ReserveListRequest request) {
        Long userId = request.getUserId();

        User user = userRepository.findById(userId).orElseThrow(
                () -> new CannotFindUser(ExceptionsType.CANNOT_FIND_USER)
        );

        List<Reservation> allReservationByUser = user.getReservations();

        return new ReserveListResponse(
                ResponseType.STORE_RESERVE_LIST_SUCCESS,
                allReservationByUser.stream()
                    .map(ReserveDetail::of).toList()
        );
    }

    /**
     * 예약하기
     * @param request 예약 요청
     * @return 예약 성공 여부
     */
    @Transactional
    @Override
    public ReserveResponse reserve(ReserveRegisterRequest request) {

        // 예약하고자 하는 가게의 Id
        Long storeId = request.getStoreId();

        // 예약하려는 사람의 Id
        Long userId = request.getUserId();

        // 요청 정보 찾기
        User requestUser = userRepository.findById(userId).orElseThrow(
                () -> new CannotFindUser(ExceptionsType.CANNOT_FIND_USER)
        );

        Store requestStore = storeRepository.findById(storeId).orElseThrow(
                () -> new CannotFindStore(ExceptionsType.CANNOT_FIND_STORE)
        );

        // 최대 입장 가능 인원 수
        Long storeMaxCapacity = requestStore.getMaxCapacity();

        // storesCache::rest::{storeId}
        // storesCache::rest::1
        // 현재 가게에 몇명의 사람이 있는지 확인
        String key = RedisKeyType.STORES + "::rest::" + storeId;
        Long nowPeople = redisTemplate.opsForValue().get(key);

        // 기존에 key값이 없었을 경우, 0으로 초기화
        if(nowPeople == null){
            nowPeople = 0L;
        }

        // 현재 가게 안의 사람과 예약하려는 인원이 최대 수용인원보다 많거나 같다면 예약 실패
        if( nowPeople + request.getPeople() >= storeMaxCapacity){
            return new ReserveResponse(ResponseType.STORE_RESERVE_FAIL_OVER_CAPACITY);
        }
        // 예약 정보 생성
        Reservation reservation = Reservation.builder()
                .reserveTime(request.getReserveTime())
                .checkInTime(null)
                .people(request.getPeople())
                .checkStatus(ReserveType.NOT_YET)
                .approvedStatus(ApprovedType.NOT_YET)
                .store(requestStore)
                .user(requestUser)
                .build();

        // 예약 정보 저장
        reservationRepository.save(reservation);

        // 예약한 사람 수 업데이트
        redisTemplate.opsForValue().set(key, nowPeople + request.getPeople());

        return new ReserveResponse(ResponseType.STORE_RESERVE_SUCCESS);
    }

    /**
     * 예약 취소
     * @param request 예약 취소 요청
     * @return 성공, 실패 여부
     */
    @Transactional
    @Override
    public ReserveCancelResponse cancel(ReserveCancelRequest request) {

        // 예약의 고유 id
        Long reserveId = request.getReserveId();

        // 예약하려는 사람의 Id
        Long userId = request.getUserId();

        // 예약 정보 찾기
        Reservation reservation = reservationRepository.findById(reserveId).orElseThrow(
                () -> new CannotFindReservation(ExceptionsType.CANNOT_FIND_RESERVATION)
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
                () -> new CannotFindReservation(ExceptionsType.CANNOT_FIND_RESERVATION)
        );

        // 예약한 사람과 취소하려는 사람이 같은지 확인
        if (!Objects.equals(reservation.getUser().getId(), userId)) {
            return new ReserveCheckInResponse(ResponseType.STORE_RESERVE_CHECKIN_FAIL, null);
        }

        // 예약 승인 여부 검사
        if(reservation.getApprovedStatus() != ApprovedType.APPROVED){
            return new ReserveCheckInResponse(ResponseType.STORE_RESERVE_CHECKIN_FAIL, null);
        }

        // 체크인 시간 업데이트
        LocalDateTime now = LocalDateTime.now();

        reservation.updateCheckInTime(now);
        reservation.updateCheckStatus(ReserveType.CHECK_IN);

        return new ReserveCheckInResponse(ResponseType.STORE_RESERVE_CHECKIN_SUCCESS,now);
    }

    /**
     * 예약 체크아웃
     * @param request 예약 체크아웃 요청
     * @return 성공, 실패 여부
     */
    @Transactional
    @Override
    public ReserveCheckOutResponse checkOut(ReserveCheckOutRequest request) {

        Long reservedId = request.getReserveId();

        Long userId = request.getUserId();

        Reservation reservation = reservationRepository.findById(reservedId).orElseThrow(
                () -> new CannotFindReservation(ExceptionsType.CANNOT_FIND_RESERVATION)
        );

        if (!Objects.equals(reservation.getUser().getId(), userId)) {
            return new ReserveCheckOutResponse(ResponseType.STORE_RESERVE_CHECKOUT_FAIL, null);
        }

        // 체크인 상태 확인
        if(reservation.getCheckStatus() != ReserveType.CHECK_IN){
            return new ReserveCheckOutResponse(ResponseType.STORE_RESERVE_CHECKOUT_FAIL, null);
        }

        // 체크아웃 시간 업데이트
        reservation.updateCheckStatus(ReserveType.CHECK_OUT);

        // 예약한 사람 수 업데이트
        Long usedPeople = reservation.getPeople();
        String key = RedisKeyType.STORES + "::rest::" + reservation.getStore().getId();
        redisTemplate.opsForValue().getAndSet(key + reservation.getStore().getId(), redisTemplate.opsForValue().get(key) - usedPeople);
        Long nowPeople = redisTemplate.opsForValue().get(key);
        redisTemplate.opsForValue().set(key, nowPeople - usedPeople);

        return new ReserveCheckOutResponse(ResponseType.STORE_RESERVE_CHECKOUT_SUCCESS, LocalDateTime.now());
    }

    /**
     * 가게의 예약 리스트 가져오기
     * @param request 가게의 예약 리스트 요청
     * @return 가게의 예약 리스트 응답
     */
    @Transactional
    @Override
    public ReserveListResponse getReservationList(ReserveRequest request) {

        log.info("request : {}", request.toString());
        Long storeId = request.getStoreId();

        Long userId = request.getUserId();

        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new CannotFindStore(ExceptionsType.CANNOT_FIND_STORE)
        );

        if (!Objects.equals(store.getManager().getId(), userId)) {
            return new ReserveListResponse(ResponseType.STORE_RESERVE_LIST_FAIL, null);
        }
        List<Reservation> allByStoreAndApprovedStatus = reservationRepository.findAllByStoreAndApprovedStatus(store, ApprovedType.NOT_YET);

        List<ReserveDetail> list = allByStoreAndApprovedStatus.stream()
                .map(ReserveDetail::of).toList();

        return new ReserveListResponse(ResponseType.STORE_RESERVE_LIST_SUCCESS, list);
    }

    /**
     * 예약 승인
     * @param request 예약 승인 요청
     * @return 성공, 실패 여부
     */
    @Transactional
    @Override
    public ReserveConfirmResponse approveReservation(ReserveConfirmRequest request) {

        Long reserveId = request.getReserveId();

        Long partnerUserId = request.getUserId();

        Long storeId = request.getStoreId();

        Reservation reservation = reservationRepository.findById(reserveId).orElseThrow(
                () -> new CannotFindReservation(ExceptionsType.CANNOT_FIND_RESERVATION)
        );

        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new CannotFindStore(ExceptionsType.CANNOT_FIND_STORE)
        );

        // 가게의 매니저와 요청한 유저가 같은지 확인
        if (!Objects.equals(store.getManager().getId(), partnerUserId)) {
            return new ReserveConfirmResponse(ResponseType.STORE_RESERVE_CONFIRM_FAIL);
        }

        // 승인
        reservation.updateApprovedStatus(ApprovedType.APPROVED);

        return new ReserveConfirmResponse(ResponseType.STORE_RESERVE_CONFIRM_SUCCESS);
    }

    /**
     * 예약 거절
     * @param request 예약 거절 요청
     * @return 성공, 실패 여부
     */
    @Transactional
    @Override
    public ReserveDenyResponse rejectReservation(ReserveDenyRequest request) {

        Long reserveId = request.getReserveId();

        Long partnerUserId = request.getUserId();

        Long storeId = request.getStoreId();

        Reservation reservation = reservationRepository.findById(reserveId).orElseThrow(
                () -> new CannotFindReservation(ExceptionsType.CANNOT_FIND_RESERVATION)
        );

        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new CannotFindStore(ExceptionsType.CANNOT_FIND_STORE)
        );

        // 가게의 매니저와 요청한 유저가 같은지 확인
        if (!Objects.equals(store.getManager().getId(), partnerUserId)) {
            return new ReserveDenyResponse(ResponseType.STORE_RESERVE_DENY_FAIL);
        }

        // 승인
        reservation.updateApprovedStatus(ApprovedType.APPROVED);

        return new ReserveDenyResponse(ResponseType.STORE_RESERVE_DENY_SUCCESS);
    }
}
