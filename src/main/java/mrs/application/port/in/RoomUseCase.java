package mrs.application.port.in;

import mrs.application.domain.model.reservation.ReservableRoom;
import mrs.application.domain.model.room.MeetingRoom;

import java.time.LocalDate;
import java.util.List;

public interface RoomUseCase {
    MeetingRoom findMeetingRoom(Integer roomId);

    List<ReservableRoom> findReservableRooms(LocalDate date);
}
