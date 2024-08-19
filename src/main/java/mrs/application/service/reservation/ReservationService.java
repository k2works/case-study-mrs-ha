package mrs.application.service.reservation;

import lombok.RequiredArgsConstructor;
import mrs.application.domain.model.ReservableRoom;
import mrs.application.domain.model.ReservableRoomId;
import mrs.application.domain.model.Reservation;
import mrs.application.port.in.ReservationUseCase;
import mrs.application.port.out.ReservableRoomPort;
import mrs.application.port.out.ReservationPort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 会議室予約サービス
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService implements ReservationUseCase {
    private final ReservableRoomPort reservableRoomPort;
    private final ReservationPort reservationPort;

    /**
     * 予約一覧を取得する
     */
    public List<Reservation> findReservations(ReservableRoomId reservableRoomId) {
        return reservationPort.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId);
    }

    /**
     * 予約を行う
     */
    public Reservation reserve(Reservation reservation) {
        ReservableRoomId reservableRoomId = reservation.getReservableRoom().getReservableRoomId();
        ReservableRoom reservable = reservableRoomPort.findOneForUpdateByReservableRoomId(reservableRoomId);
        if (reservable == null) {
            throw new UnavailableReservationException("入力の日付・部屋の組合わせは予約できません。");
        }
        boolean overlap = reservationPort.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId)
                .stream()
                .anyMatch(x -> x.overlap(reservation));
        if (overlap) {
            throw new AlreadyReservedException("入力の時間帯はすでに予約済みです。");
        }
        reservationPort.save(reservation);
        return reservation;
    }

    /**
     * 予約を取消す
     */
    @PreAuthorize("hasRole('ADMIN') or #reservation.user.userId == principal.user.userId")
    public void cancel(@P("reservation") Reservation reservation) {
        reservationPort.delete(reservation);
    }

    /**
     * 予約情報を取得する
     */
    public Reservation findOne(Integer reservationId) {
        Reservation reservation = reservationPort.findById(reservationId);
        if (reservation == null) {
            throw new IllegalStateException("予約が見つかりません。");
        }
        return reservation;
    }
}
