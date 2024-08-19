package mrs.infrastructure.persistence;

import mrs.domain.model.Reservation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationMapper {
    Reservation mapToDomainEntity(ReservationJpaEntity reservationJpaEntity) {
        return new Reservation(
                reservationJpaEntity.getReservationId(),
                reservationJpaEntity.getStartTime(),
                reservationJpaEntity.getEndTime(),
                new ReservableRoomMapper().mapToDomainEntity(reservationJpaEntity.getReservableRoom()),
                new UserMapper().mapToDomainEntity(reservationJpaEntity.getUser()));
    }

    ReservationJpaEntity mapToJpaEntity(Reservation reservation) {
        return new ReservationJpaEntity(
                reservation.getReservationId(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                new ReservableRoomMapper().mapToJpaEntity(reservation.getReservableRoom()),
                new UserMapper().mapToJpaEntity(reservation.getUser()));
    }

    public List<Reservation> mapToDomainEntities(List<ReservationJpaEntity> byReservableRoomReservableRoomIdOrderByStartTimeAsc) {
        return byReservableRoomReservableRoomIdOrderByStartTimeAsc.stream()
                .map(this::mapToDomainEntity)
                .toList();
    }
}
