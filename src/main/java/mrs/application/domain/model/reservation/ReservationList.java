package mrs.application.domain.model.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.Objects;

/**
 * 予約一覧
 */
@Value
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ReservationList {
    List<Reservation> value;

    public static ReservationList of(List<Reservation> value) {
        return new ReservationList(value);
    }

    public int size() {
        return Objects.requireNonNull(value).size();
    }
}
