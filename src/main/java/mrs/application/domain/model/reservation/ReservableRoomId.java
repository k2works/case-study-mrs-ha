package mrs.application.domain.model.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;
import mrs.application.domain.model.room.RoomId;

import java.time.LocalDate;

/**
 * 予約可能な会議室のID
 */
@Getter
@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ReservableRoomId {
    RoomId roomId;
    ReservedDate reservedDate;

    public static ReservableRoomId of(Integer roomId, LocalDate reservedDate) {
        return new ReservableRoomId(new RoomId(roomId), new ReservedDate(reservedDate));
    }
}
