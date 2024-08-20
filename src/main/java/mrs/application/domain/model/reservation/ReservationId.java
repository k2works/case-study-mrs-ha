package mrs.application.domain.model.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

/**
 * 予約ID
 */
@Value
@Getter
@AllArgsConstructor
public class ReservationId {
    Integer value;
}
