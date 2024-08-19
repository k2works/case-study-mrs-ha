package mrs.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import mrs.domain.model.ReservableRoomId;
import mrs.domain.model.Reservation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ReservationPersistenceAdapter {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public void save(Reservation reservation) {
        reservationRepository.save(reservationMapper.mapToJpaEntity(reservation));
    }

    public void deleteAll() {
        reservationRepository.deleteAll();
    }

    public List<Reservation> findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(ReservableRoomId reservableRoomId) {
        ReservableRoomIdJpaEntity reservableRoomIdJpaEntity = new ReservableRoomIdJpaEntity(reservableRoomId.getRoomId(), reservableRoomId.getReservedDate());
        return reservationMapper.mapToDomainEntities(reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomIdJpaEntity));
    }

    public void delete(Reservation reservation) {
        reservationRepository.delete(reservationMapper.mapToJpaEntity(reservation));
    }

    public Reservation findById(Integer reservationId) {
        return reservationMapper.mapToDomainEntity(Objects.requireNonNull(reservationRepository.findById(reservationId).orElse(null)));
    }
}
