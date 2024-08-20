package mrs.infrastructure.out.persistence.reservation;

import mrs.application.domain.model.reservation.ReservableRoom;
import mrs.application.domain.model.reservation.ReservableRoomId;
import mrs.application.domain.model.room.MeetingRoom;
import mrs.infrastructure.out.persistence.room.MeetingRoomJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservableRoomMapper {
    ReservableRoom mapToDomainEntity(ReservableRoomJpaEntity reservableRoomJpaEntity) {
        return new ReservableRoom(
                ReservableRoomId.of(
                        reservableRoomJpaEntity.getReservableRoomId().getRoomId(),
                        reservableRoomJpaEntity.getReservableRoomId().getReservedDate()),
                MeetingRoom.of(reservableRoomJpaEntity.getMeetingRoomJpaEntity().getRoomId(),
                        reservableRoomJpaEntity.getMeetingRoomJpaEntity().getRoomName()));
    }

    ReservableRoomJpaEntity mapToJpaEntity(ReservableRoom reservableRoom) {
        return new ReservableRoomJpaEntity(
                new ReservableRoomIdJpaEntity(
                        reservableRoom.getReservableRoomId().getRoomId().getValue(),
                        reservableRoom.getReservableRoomId().getReservedDate().getValue()),
                new MeetingRoomJpaEntity(
                        reservableRoom.getMeetingRoom().getRoomId().getValue(),
                        reservableRoom.getMeetingRoom().getRoomName().getValue()));
    }

    public List<ReservableRoom> mapToDomainEntityList(List<ReservableRoomJpaEntity> byReservableRoomIdReservedDateOrderByReservableRoomIdRoomIdAsc) {
        return byReservableRoomIdReservedDateOrderByReservableRoomIdRoomIdAsc.stream()
                .map(this::mapToDomainEntity)
                .toList();
    }
}
