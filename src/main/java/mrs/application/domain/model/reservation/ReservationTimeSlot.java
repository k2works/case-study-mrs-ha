package mrs.application.domain.model.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.time.LocalTime;

/**
 * 予約時間帯
 */
@Value
@Getter
@AllArgsConstructor
public class ReservationTimeSlot {
    LocalTime startTime;

    LocalTime endTime;
}
