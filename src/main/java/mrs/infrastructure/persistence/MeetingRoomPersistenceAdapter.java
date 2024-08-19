package mrs.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import mrs.domain.model.MeetingRoom;
import mrs.infrastructure.repository.room.MeetingRoomRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MeetingRoomPersistenceAdapter {
    private final MeetingRoomRepository meetingRoomRepository;
    private final MeetingRoomMapper meetingRoomMapper;

    public MeetingRoom findById(Integer roomId) {
        return meetingRoomMapper.mapToDomainEntity(meetingRoomRepository.findById(roomId).orElseThrow());
    }

    public void save(MeetingRoom room) {
        meetingRoomRepository.save(meetingRoomMapper.mapToJpaEntity(room));
    }
}
