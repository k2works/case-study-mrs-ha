package mrs.service.room;

import mrs.domain.model.MeetingRoom;
import mrs.domain.model.ReservableRoom;
import mrs.infrastructure.persistence.reservation.ReservableRoomPersistenceAdapter;
import mrs.infrastructure.persistence.room.MeetingRoomPersistenceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * 会議室サービス
 */
@Service
@Transactional
public class RoomService {
    @Autowired
    ReservableRoomPersistenceAdapter reservableRoomRepository;
    @Autowired
    MeetingRoomPersistenceAdapter meetingRoomRepository;

    /**
     * 会議室を取得する
     */
    public MeetingRoom findMeetingRoom(Integer roomId) {
        MeetingRoom meetingRoom = meetingRoomRepository.findById(roomId);
        if (meetingRoom == null) {
            throw new IllegalStateException("会議室が見つかりません。");
        }
        return meetingRoom;
    }

    /**
     * 予約可能会議室を取得する
     */
    public List<ReservableRoom> findReservableRooms(LocalDate date) {
        return reservableRoomRepository.findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(date);
    }
}
