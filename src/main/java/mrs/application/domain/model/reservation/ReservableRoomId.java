package mrs.application.domain.model.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

/**
 * 予約可能な会議室のID
 */
@Getter
@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ReservableRoomId {
    Integer roomId;
    LocalDate reservedDate;
}
