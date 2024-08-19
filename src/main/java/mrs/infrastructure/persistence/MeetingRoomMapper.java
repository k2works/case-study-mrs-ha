package mrs.infrastructure.persistence;

import mrs.domain.model.MeetingRoom;
import org.springframework.stereotype.Component;

@Component
public class MeetingRoomMapper {
    MeetingRoom mapToDomainEntity(MeetingRoomJpaEntity meetingRoomJpaEntity) {
        return new MeetingRoom(meetingRoomJpaEntity.getRoomId(), meetingRoomJpaEntity.getRoomName());
    }

    MeetingRoomJpaEntity mapToJpaEntity(MeetingRoom meetingRoom) {
        return new MeetingRoomJpaEntity(meetingRoom.getRoomId(), meetingRoom.getRoomName());
    }
}
