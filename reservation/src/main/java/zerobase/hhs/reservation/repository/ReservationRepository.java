package zerobase.hhs.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.hhs.reservation.domain.Reservation;
import zerobase.hhs.reservation.domain.Store;
import zerobase.hhs.reservation.type.ApprovedType;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findAllByStoreAndApprovedStatus(Store store, ApprovedType approvedStatus);
}
