package mrs.application.domain.model.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDate;

/**
 * 予約日
 */
@Getter
@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ReservedDate {
    LocalDate value;
}
