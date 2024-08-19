package mrs.infrastructure.persistence.room;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会議室
 */
@Entity
@Table(name = "meeting_room")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingRoomJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomId;

    private String roomName;
}
