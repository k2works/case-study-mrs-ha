package mrs.infrastructure.persistence;

import mrs.domain.model.MeetingRoom;
import mrs.domain.model.ReservableRoom;
import mrs.domain.model.ReservableRoomId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservableRoomMapper {
    ReservableRoom mapToDomainEntity(ReservableRoomJpaEntity reservableRoomJpaEntity) {
        return new ReservableRoom(
                new ReservableRoomId(
                        reservableRoomJpaEntity.getReservableRoomId().getRoomId(),
                        reservableRoomJpaEntity.getReservableRoomId().getReservedDate()),
                new MeetingRoom(reservableRoomJpaEntity.getMeetingRoomJpaEntity().getRoomId(),
                        reservableRoomJpaEntity.getMeetingRoomJpaEntity().getRoomName()));
    }

    ReservableRoomJpaEntity mapToJpaEntity(ReservableRoom reservableRoom) {
        return new ReservableRoomJpaEntity(
                new ReservableRoomIdJpaEntity(
                        reservableRoom.getReservableRoomId().getRoomId(),
                        reservableRoom.getReservableRoomId().getReservedDate()),
                new MeetingRoomJpaEntity(
                        reservableRoom.getMeetingRoom().getRoomId(),
                        reservableRoom.getMeetingRoom().getRoomName()));
    }

    public List<ReservableRoom> mapToDomainEntityList(List<ReservableRoomJpaEntity> byReservableRoomIdReservedDateOrderByReservableRoomIdRoomIdAsc) {
        return byReservableRoomIdReservedDateOrderByReservableRoomIdRoomIdAsc.stream()
                .map(this::mapToDomainEntity)
                .toList();
    }
}
