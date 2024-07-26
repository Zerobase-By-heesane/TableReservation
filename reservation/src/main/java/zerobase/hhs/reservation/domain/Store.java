package zerobase.hhs.reservation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.hhs.reservation.dto.request.store.StoreUpdateRequest;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "store")
public class Store extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="store_id")
    private Long id;

    // 가게 이름
    @Column(name="store_name")
    private String name;

    // 가게 주소
    @Column(name="store_address")
    private String address;

    // 가게 전화번호
    @Column(name="store_pn")
    private String phoneNumber;

    // 가게 설명
    @Column(name="store_desc")
    private String description;

    // 가게 최대 수용인원
    @Column(name="max_capacity")
    private Long maxCapacity;

    // 가게 사장 이름
    @ManyToOne
    @JoinColumn(name="user_id")
    private User manager;

    @OneToMany(mappedBy="store")
    private List<Reservation> reservations;

    public void update(StoreUpdateRequest store){
        this.name = store.getStoreName();
        this.address = store.getStoreAddress();
        this.phoneNumber = store.getStorePn();
        this.description = store.getStoreDesc();
        this.maxCapacity = store.getStoreCA();
    }
}
