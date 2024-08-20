package mrs.application.domain.model.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

/**
 * 会議室ID
 */
@Value
@Getter
@AllArgsConstructor
public class RoomId {
    Integer value;
}
