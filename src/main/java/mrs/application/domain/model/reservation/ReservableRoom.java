package mrs.application.domain.model.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;
import mrs.application.domain.model.room.MeetingRoom;

/**
 * 特定の日に予約可能な会議室
 */
@Value
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ReservableRoom {
    ReservableRoomId reservableRoomId;

    MeetingRoom meetingRoom;

    public ReservableRoom(ReservableRoomId reservableRoomId) {
        this.reservableRoomId = reservableRoomId;
        this.meetingRoom = MeetingRoom.of(reservableRoomId.getRoomId(), null);
    }
}
