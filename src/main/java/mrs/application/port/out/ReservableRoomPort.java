package mrs.application.port.out;

import mrs.application.domain.model.reservation.ReservableRoom;
import mrs.application.domain.model.reservation.ReservableRoomId;

import java.time.LocalDate;
import java.util.List;

/**
 * 予約可能な会議室ポート
 */
public interface ReservableRoomPort {
    ReservableRoom findById(ReservableRoomId roomId);

    List<ReservableRoom> findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(LocalDate date);

    ReservableRoom findOneForUpdateByReservableRoomId(ReservableRoomId reservableRoomId);
}
