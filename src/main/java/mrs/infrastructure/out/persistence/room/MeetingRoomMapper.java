package mrs.infrastructure.out.persistence.room;

import mrs.application.domain.model.room.MeetingRoom;
import org.springframework.stereotype.Component;

@Component
public class MeetingRoomMapper {
    MeetingRoom mapToDomainEntity(MeetingRoomJpaEntity meetingRoomJpaEntity) {
        return MeetingRoom.of(meetingRoomJpaEntity.getRoomId(), meetingRoomJpaEntity.getRoomName());
    }

    MeetingRoomJpaEntity mapToJpaEntity(MeetingRoom meetingRoom) {
        return new MeetingRoomJpaEntity(meetingRoom.getRoomId().getValue(), meetingRoom.getRoomName().getValue());
    }
}
