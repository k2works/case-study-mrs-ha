package mrs.infrastructure.persistence.room;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoomJpaEntity, Integer> {
}
