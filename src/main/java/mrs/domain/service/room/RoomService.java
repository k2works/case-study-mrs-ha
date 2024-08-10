package mrs.domain.service.room;

import mrs.domain.model.ReservableRoom;
import mrs.domain.repository.room.ReservableRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService {
    @Autowired
    ReservableRoomRepository reservableRoomRepository;

    public List<ReservableRoom> findReservableRooms(LocalDate date) {
        return reservableRoomRepository.findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(date);
    }
}