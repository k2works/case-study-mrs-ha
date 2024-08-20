package mrs.application.port.in;

import mrs.application.domain.model.reservation.ReservableRoomList;
import mrs.application.domain.model.reservation.ReservedDate;
import mrs.application.domain.model.room.MeetingRoom;
import mrs.application.domain.model.room.RoomId;

public interface RoomUseCase {
    MeetingRoom findMeetingRoom(RoomId roomId);

    ReservableRoomList findReservableRooms(ReservedDate date);
}
