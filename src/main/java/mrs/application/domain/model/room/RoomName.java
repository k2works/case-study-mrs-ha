package mrs.application.domain.model.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

/**
 * 会議室名
 */
@Value
@Getter
@AllArgsConstructor
public class RoomName {
    String value;
}
