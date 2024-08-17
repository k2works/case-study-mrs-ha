package mrs.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Embeddable
public class ReservableRoomId implements Serializable {
    Integer roomId;
    LocalDate reservedDate;
}
