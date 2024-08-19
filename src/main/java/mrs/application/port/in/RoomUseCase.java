package mrs.application.port.in;

import mrs.application.domain.model.MeetingRoom;
import mrs.application.domain.model.ReservableRoom;

import java.time.LocalDate;
import java.util.List;

public interface RoomUseCase {
    public MeetingRoom findMeetingRoom(Integer roomId);

    public List<ReservableRoom> findReservableRooms(LocalDate date);
}
