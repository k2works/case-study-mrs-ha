package mrs.infrastructure.out.persistence.reservation;

import mrs.application.domain.model.reservation.Reservation;
import mrs.application.domain.model.reservation.ReservationId;
import mrs.infrastructure.out.persistence.auth.UserMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReservationMapper {
    Reservation mapToDomainEntity(ReservationJpaEntity reservationJpaEntity) {
        return Reservation.of(
                reservationJpaEntity.getReservationId(),
                reservationJpaEntity.getStartTime(),
                reservationJpaEntity.getEndTime(),
                new ReservableRoomMapper().mapToDomainEntity(reservationJpaEntity.getReservableRoom()),
                new UserMapper().mapToDomainEntity(reservationJpaEntity.getUser()));
    }

    ReservationJpaEntity mapToJpaEntity(Reservation reservation) {
        return new ReservationJpaEntity(
                Optional.ofNullable(reservation.getReservationId()).map(ReservationId::getValue).orElse(null),
                reservation.getReservationTimeSlot().getStartTime(),
                reservation.getReservationTimeSlot().getEndTime(),
                new ReservableRoomMapper().mapToJpaEntity(reservation.getReservableRoom()),
                new UserMapper().mapToJpaEntity(reservation.getUser()));
    }

    public List<Reservation> mapToDomainEntities(List<ReservationJpaEntity> byReservableRoomReservableRoomIdOrderByStartTimeAsc) {
        return byReservableRoomReservableRoomIdOrderByStartTimeAsc.stream()
                .map(this::mapToDomainEntity)
                .toList();
    }
}
