package zerobase.hhs.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.hhs.reservation.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
