package mrs.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

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
        this.meetingRoom = new MeetingRoom(reservableRoomId.getRoomId(), null);
    }
}
