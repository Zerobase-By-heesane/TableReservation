package zerobase.hhs.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.hhs.reservation.dto.request.reserve.*;
import zerobase.hhs.reservation.dto.response.reserve.*;
import zerobase.hhs.reservation.service.ReservationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/reservation")
public class UserReservationController {

    private final ReservationService reservationService;

    @GetMapping("/list")
    public ResponseEntity<ReserveListResponse> getMyReservation(ReserveListRequest request){
        return ResponseEntity.ok(reservationService.getMyReservationList(request));
    }

    @PostMapping("/reserve")
    public ResponseEntity<ReserveResponse> reserve(ReserveRegisterRequest request){
        return ResponseEntity.ok(reservationService.reserve(request));
    }

    @PostMapping("/cancel")
    public ResponseEntity<ReserveCancelResponse> cancel(ReserveCancelRequest request){
        return ResponseEntity.ok(reservationService.cancel(request));
    }

    @PostMapping("/checkin")
    public ResponseEntity<ReserveCheckInResponse> checkIn(ReserveCheckInRequest request){
        return ResponseEntity.ok(reservationService.checkIn(request));
    }

    @PostMapping("/checkout")
    public ResponseEntity<ReserveCheckOutResponse> checkOut(ReserveCheckOutRequest request){
        return ResponseEntity.ok(reservationService.checkOut(request));
    }
}
