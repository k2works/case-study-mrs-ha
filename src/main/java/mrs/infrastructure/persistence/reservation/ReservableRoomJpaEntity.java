package mrs.infrastructure.persistence.reservation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mrs.infrastructure.persistence.room.MeetingRoomJpaEntity;

/**
 * 特定の日に予約可能な会議室
 */
@Entity
@Table(name = "reservable_room")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservableRoomJpaEntity {
    @EmbeddedId
    private ReservableRoomIdJpaEntity reservableRoomId;

    @ManyToOne
    @JoinColumn(name = "room_id", insertable = false, updatable = false)
    @MapsId("roomId")
    private MeetingRoomJpaEntity meetingRoomJpaEntity;

    public ReservableRoomJpaEntity(ReservableRoomIdJpaEntity reservableRoomId) {
        this.reservableRoomId = reservableRoomId;
    }

}
