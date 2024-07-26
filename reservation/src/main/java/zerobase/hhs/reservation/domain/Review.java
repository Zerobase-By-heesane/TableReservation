package zerobase.hhs.reservation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="review")
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Review extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(name="fulfillment", nullable = false)
    private Integer fulfillment;

    @Column(name="content",nullable = false)
    private String content;

    // 1 대 1 매핑
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public void updateReview(Integer fulfillment, String content){
        this.fulfillment = fulfillment;
        this.content = content;
    }
}
