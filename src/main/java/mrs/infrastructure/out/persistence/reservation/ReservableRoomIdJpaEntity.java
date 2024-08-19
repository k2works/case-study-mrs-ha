package mrs.infrastructure.out.persistence.reservation;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 予約可能な会議室のID
 */
@Getter
@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Embeddable
public class ReservableRoomIdJpaEntity implements Serializable {
    Integer roomId;
    LocalDate reservedDate;
}
