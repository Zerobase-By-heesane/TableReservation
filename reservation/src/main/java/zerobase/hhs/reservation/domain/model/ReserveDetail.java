package zerobase.hhs.reservation.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import zerobase.hhs.reservation.domain.Reservation;
import zerobase.hhs.reservation.type.ApprovedType;
import zerobase.hhs.reservation.type.ReserveType;

import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@Getter
public class ReserveDetail {

    // 식당 예약한 사람
    private final String reserveUserNickname;

    // 식당 이름
    private final String reservedStoreName;

    // 식당 주소
    private final String reservedStoreAddress;

    // 식당 예약 시간
    private final LocalDateTime reservedTime;

    // 식당 예약 인원
    private final Long reservedPeople;

    // 체크인 상태
    private final ReserveType isCheckIn;

    // 예약 승인 여부
    private final ApprovedType isApproved;

    public static ReserveDetail of(Reservation reservation) {
        return ReserveDetail.builder()
                .reserveUserNickname(reservation.getUser().getName())
                .reservedStoreName(reservation.getStore().getName())
                .reservedStoreAddress(reservation.getStore().getAddress())
                .reservedTime(reservation.getReserveTime())
                .reservedPeople(reservation.getPeople())
                .isCheckIn(reservation.getCheckStatus())
                .isApproved(reservation.getApprovedStatus())
                .build();
    }
}
