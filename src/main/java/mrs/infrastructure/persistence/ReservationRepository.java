package mrs.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationJpaEntity, Integer> {
    List<ReservationJpaEntity> findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(ReservableRoomIdJpaEntity reservableRoomId);
}
