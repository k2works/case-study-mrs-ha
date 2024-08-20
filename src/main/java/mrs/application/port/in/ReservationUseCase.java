package mrs.application.port.in;

import mrs.application.domain.model.reservation.ReservableRoomId;
import mrs.application.domain.model.reservation.Reservation;
import mrs.application.domain.model.reservation.ReservationId;

import java.util.List;

public interface ReservationUseCase {
    List<Reservation> findReservations(ReservableRoomId reservableRoomId);

    Reservation reserve(Reservation reservation);

    void cancel(Reservation reservation);

    Reservation findOne(ReservationId reservationId);
}
