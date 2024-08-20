package mrs.application.domain.model.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.Objects;

/**
 * 予約可能な会議室一覧
 */
@Value
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ReservableRoomList {
    List<ReservableRoom> value;

    public static ReservableRoomList of(List<ReservableRoom> value) {
        return new ReservableRoomList(value);
    }

    public int size() {
        return Objects.requireNonNull(value).size();
    }
}
