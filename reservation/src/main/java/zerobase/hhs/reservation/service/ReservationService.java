package zerobase.hhs.reservation.service;

import zerobase.hhs.reservation.dto.request.reserve.*;
import zerobase.hhs.reservation.dto.response.reserve.*;

public interface ReservationService {

    // 유저
    // 내 예약 목록
    ReserveListResponse getMyReservationList(ReserveListRequest request);

    // 예약
    ReserveResponse reserve(ReserveRegisterRequest request);

    // 취소
    ReserveCancelResponse cancel(ReserveCancelRequest request);

    // 체크인
    ReserveCheckInResponse checkIn(ReserveCheckInRequest request);

    // 체크아웃
    ReserveCheckOutResponse checkOut(ReserveCheckOutRequest request);

    // 파트너
    // 예약 목록
    ReserveListResponse getReservationList(ReserveRequest request);

    // 예약 승인
    ReserveConfirmResponse approveReservation(ReserveConfirmRequest request);

    // 예약 거부
    ReserveDenyResponse rejectReservation(ReserveDenyRequest request);

}