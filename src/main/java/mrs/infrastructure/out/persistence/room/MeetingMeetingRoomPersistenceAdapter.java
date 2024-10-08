package mrs.infrastructure.out.persistence.room;

import lombok.RequiredArgsConstructor;
import mrs.application.domain.model.room.MeetingRoom;
import mrs.application.port.out.MeetingRoomPort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MeetingMeetingRoomPersistenceAdapter implements MeetingRoomPort {
    private final MeetingRoomRepository meetingRoomRepository;
    private final MeetingRoomMapper meetingRoomMapper;

    public MeetingRoom findById(Integer roomId) {
        return meetingRoomMapper.mapToDomainEntity(meetingRoomRepository.findById(roomId).orElseThrow());
    }

    public void save(MeetingRoom room) {
        meetingRoomRepository.save(meetingRoomMapper.mapToJpaEntity(room));
    }
}
