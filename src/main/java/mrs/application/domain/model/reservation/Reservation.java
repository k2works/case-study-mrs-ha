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
    ReservationId reservationId;

    ReservationTimeSlot reservationTimeSlot;

    ReservableRoom reservableRoom;

    User user;

    public Reservation(@NotNull(message = "必須です") LocalTime startTime, @NotNull(message = "必須です") LocalTime endTime, ReservableRoom reservableRoom, User user) {
        this.reservationId = null;
        this.reservationTimeSlot = new ReservationTimeSlot(startTime, endTime);
        this.reservableRoom = reservableRoom;
        this.user = user;
    }

    public static Reservation of(Integer reservationId, LocalTime startTime, LocalTime endTime, ReservableRoom reservableRoom, User user) {
        return new Reservation(new ReservationId(reservationId), new ReservationTimeSlot(startTime, endTime), reservableRoom, user);
    }

    public boolean overlap(Reservation target) {
        if (!Objects.equals(Objects.requireNonNull(reservableRoom).getReservableRoomId(), Objects.requireNonNull(target.reservableRoom).getReservableRoomId())) {
            return false;
        }

        if (Objects.requireNonNull(reservationTimeSlot).equals(target.getReservationTimeSlot())) {
            return true;
        }

        LocalTime startTime = Objects.requireNonNull(getReservationTimeSlot().getStartTime());
        LocalTime endTime = Objects.requireNonNull(getReservationTimeSlot().getEndTime());
        LocalTime targetStartTime = Objects.requireNonNull(target.getReservationTimeSlot().getStartTime());
        LocalTime targetEndTime = Objects.requireNonNull(target.getReservationTimeSlot().getEndTime());

        return targetEndTime.isAfter(startTime) && endTime.isAfter(targetStartTime);
    }
}
