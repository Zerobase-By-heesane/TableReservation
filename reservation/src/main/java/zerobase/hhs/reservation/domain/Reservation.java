package zerobase.hhs.reservation.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import zerobase.hhs.reservation.type.ApprovedType;
import zerobase.hhs.reservation.type.ReserveType;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "reserve_time", nullable = false)
    private LocalDateTime reserveTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    @Column(name = "people", nullable = false)
    private Long people;

    @Column(name = "check_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReserveType checkStatus;

    @Column(name = "approved_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ApprovedType approvedStatus;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void updateCheckInTime(LocalDateTime time){
        this.checkInTime = time;
    }

    public void updateApprovedStatus(ApprovedType approvedStatus){
        this.approvedStatus = approvedStatus;
    }

    public void updateCheckStatus(ReserveType checkStatus){
        this.checkStatus = checkStatus;
    }
}
