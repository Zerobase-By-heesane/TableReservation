package zerobase.hhs.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.hhs.reservation.domain.Store;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    // 삭제되지 않은 모든 가게 조회
    List<Store> findAllByDeletedAtIsNull();

    // 가게 이름으로 가게 조회
    Optional<Store> findByName(String name);

    // 가게 삭제
    void deleteByIdAndDeletedAtIsNull(Long id);
}
