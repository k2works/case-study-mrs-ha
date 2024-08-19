package mrs.infrastructure.persistence.reservation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mrs.infrastructure.persistence.user.UserJpaEntity;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * 予約情報
 */
@Entity
@Table(name = "reservation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationJpaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;

    private LocalTime startTime;

    private LocalTime endTime;

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "reserved_date"), @JoinColumn(name = "room_id")})
    private ReservableRoomJpaEntity reservableRoom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;
}
