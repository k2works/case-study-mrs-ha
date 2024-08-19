package mrs.infrastructure.repository.room;

import mrs.infrastructure.persistence.MeetingRoomJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoomJpaEntity, Integer> {
}
