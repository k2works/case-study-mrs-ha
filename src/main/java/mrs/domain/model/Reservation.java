package mrs.domain.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalTime;
import java.util.Objects;

/**
 * 予約情報
 */
@Value
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Reservation {
    Integer reservationId;

    LocalTime startTime;

    LocalTime endTime;

    ReservableRoom reservableRoom;

    User user;

    public Reservation(@NotNull(message = "必須です") LocalTime startTime, @NotNull(message = "必須です") LocalTime endTime, ReservableRoom reservableRoom, User user) {
        this.reservationId = null;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reservableRoom = reservableRoom;
        this.user = user;
    }

    public boolean overlap(Reservation target) {
        if (!Objects.equals(reservableRoom.getReservableRoomId(), target.reservableRoom.getReservableRoomId())) {
            return false;
        }
        if (startTime.equals(target.startTime) && endTime.equals(target.endTime)) {
            return true;
        }
        return target.endTime.isAfter(startTime) && endTime.isAfter(target.startTime);
    }
}
