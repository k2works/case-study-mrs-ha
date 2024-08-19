package mrs.infrastructure.repository.room;

import jakarta.persistence.LockModeType;
import mrs.infrastructure.persistence.ReservableRoomIdJpaEntity;
import mrs.infrastructure.persistence.ReservableRoomJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDate;
import java.util.List;

public interface ReservableRoomRepository extends JpaRepository<ReservableRoomJpaEntity, ReservableRoomIdJpaEntity> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    ReservableRoomJpaEntity findOneForUpdateByReservableRoomId(ReservableRoomIdJpaEntity reservableRoomId);

    List<ReservableRoomJpaEntity> findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(LocalDate reservedDate);
}
