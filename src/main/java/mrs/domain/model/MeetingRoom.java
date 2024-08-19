package mrs.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

/**
 * 会議室
 */
@Value
@Getter
@AllArgsConstructor
public class MeetingRoom {
    Integer roomId;

    String roomName;
}
