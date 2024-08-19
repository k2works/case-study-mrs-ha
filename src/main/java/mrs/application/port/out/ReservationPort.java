package mrs.application.port.out;

import mrs.application.domain.model.ReservableRoomId;
import mrs.application.domain.model.Reservation;

import java.util.List;

/**
 * 予約ポート
 */
public interface ReservationPort {
    List<Reservation> findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(ReservableRoomId reservableRoomId);

    Reservation findById(Integer reservationId);

    void save(Reservation reservation);

    void delete(Reservation reservation);
}
