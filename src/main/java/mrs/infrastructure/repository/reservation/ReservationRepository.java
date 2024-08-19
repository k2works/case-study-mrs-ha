package mrs.infrastructure.repository.reservation;

import mrs.infrastructure.persistence.ReservableRoomIdJpaEntity;
import mrs.infrastructure.persistence.ReservationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationJpaEntity, Integer> {
    List<ReservationJpaEntity> findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(ReservableRoomIdJpaEntity reservableRoomId);
}
