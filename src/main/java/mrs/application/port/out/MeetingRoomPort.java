package mrs.application.port.out;

import mrs.application.domain.model.room.MeetingRoom;

/**
 * 会議室情報を取得するためのポート
 */
public interface MeetingRoomPort {
    MeetingRoom findById(Integer roomId);
}
