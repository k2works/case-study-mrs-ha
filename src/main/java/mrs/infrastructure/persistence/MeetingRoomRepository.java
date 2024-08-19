package mrs.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoomJpaEntity, Integer> {
}
