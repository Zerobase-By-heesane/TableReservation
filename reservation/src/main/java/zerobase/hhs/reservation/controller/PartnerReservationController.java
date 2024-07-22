package zerobase.hhs.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zerobase.hhs.reservation.dto.request.reserve.ReserveConfirmRequest;
import zerobase.hhs.reservation.dto.request.reserve.ReserveDenyRequest;
import zerobase.hhs.reservation.dto.request.reserve.ReserveRequest;
import zerobase.hhs.reservation.dto.response.reserve.ReserveConfirmResponse;
import zerobase.hhs.reservation.dto.response.reserve.ReserveDenyResponse;
import zerobase.hhs.reservation.dto.response.reserve.ReserveListResponse;
import zerobase.hhs.reservation.service.ReservationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/partner/reservation")
public class PartnerReservationController {

    private final ReservationService reservationService;

    @GetMapping("/list")
    public ResponseEntity<ReserveListResponse> getReservationList(ReserveRequest request){
        return ResponseEntity.ok(reservationService.getReservationList(request));
    }

    @GetMapping("/confirm")
    public ResponseEntity<ReserveConfirmResponse> confirmReservation(ReserveConfirmRequest request){
        return ResponseEntity.ok(reservationService.approveReservation(request));
    }

    @GetMapping("/cancel")
    public ResponseEntity<ReserveDenyResponse> cancelReservation(ReserveDenyRequest request){
        return ResponseEntity.ok(reservationService.rejectReservation(request));
    }

}
