package mrs.application.domain.model.room;

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
    RoomId roomId;

    RoomName roomName;

    public static MeetingRoom of(Integer roomId, String roomName) {
        return new MeetingRoom(new RoomId(roomId), new RoomName(roomName));
    }
}
