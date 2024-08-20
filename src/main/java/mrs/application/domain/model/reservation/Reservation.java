package mrs.application.domain.model.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;
import mrs.application.domain.model.auth.User;

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
        if (!Objects.equals(Objects.requireNonNull(reservableRoom).getReservableRoomId(), Objects.requireNonNull(target.reservableRoom).getReservableRoomId())) {
            return false;
        }
        if (Objects.requireNonNull(startTime).equals(target.startTime) && Objects.requireNonNull(endTime).equals(target.endTime)) {
            return true;
        }
        return Objects.requireNonNull(target.endTime).isAfter(startTime) && Objects.requireNonNull(endTime).isAfter(target.startTime);
    }
}
