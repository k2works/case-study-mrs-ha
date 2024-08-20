package mrs.application.port.in;

import mrs.application.domain.model.reservation.ReservableRoom;
import mrs.application.domain.model.reservation.ReservedDate;
import mrs.application.domain.model.room.MeetingRoom;
import mrs.application.domain.model.room.RoomId;

import java.util.List;

public interface RoomUseCase {
    MeetingRoom findMeetingRoom(RoomId roomId);

    List<ReservableRoom> findReservableRooms(ReservedDate date);
}
