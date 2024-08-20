package mrs.application.service.room;

import lombok.RequiredArgsConstructor;
import mrs.application.domain.model.reservation.ReservableRoomList;
import mrs.application.domain.model.reservation.ReservedDate;
import mrs.application.domain.model.room.MeetingRoom;
import mrs.application.domain.model.room.RoomId;
import mrs.application.port.in.RoomUseCase;
import mrs.application.port.out.MeetingRoomPort;
import mrs.application.port.out.ReservableRoomPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 会議室サービス
 */
@Service
@Transactional
@RequiredArgsConstructor
public class RoomService implements RoomUseCase {
    private final ReservableRoomPort reservableRoomPort;
    private final MeetingRoomPort meetingRoomPort;


    /**
     * 会議室を取得する
     */
    public MeetingRoom findMeetingRoom(RoomId roomId) {
        MeetingRoom meetingRoom = meetingRoomPort.findById(roomId.getValue());
        if (meetingRoom == null) {
            throw new IllegalStateException("会議室が見つかりません。");
        }
        return meetingRoom;
    }

    /**
     * 予約可能会議室を取得する
     */
    public ReservableRoomList findReservableRooms(ReservedDate date) {
        return ReservableRoomList.of(reservableRoomPort.findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(date.getValue()));
    }
}
