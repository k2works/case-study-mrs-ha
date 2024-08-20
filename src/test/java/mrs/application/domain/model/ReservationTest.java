package mrs.application.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationTest {

    @Test
    @DisplayName("異なる部屋の予約は重複しない")
    void testOverlapWithDifferentRoomId() {
        ReservableRoomId reservableRoomId1 = new ReservableRoomId(1, null);
        ReservableRoomId reservableRoomId2 = new ReservableRoomId(2, null);
        ReservableRoom reservableRoom1 = new ReservableRoom(reservableRoomId1);
        ReservableRoom reservableRoom2 = new ReservableRoom(reservableRoomId2);
        Reservation reservation1 = new Reservation(LocalTime.of(11, 0), LocalTime.of(12, 0), reservableRoom1, null);
        Reservation reservation2 = new Reservation(LocalTime.of(11, 0), LocalTime.of(12, 0), reservableRoom2, null);
        assertFalse(reservation1.overlap(reservation2));
    }

    @Test
    @DisplayName("同じ部屋の予約で開始時刻が同じで終了時刻が異なる場合は重複する")
    void testOverlapWithSameRoomIdAndExactTimes() {
        ReservableRoomId reservableRoomId = new ReservableRoomId(1, null);
        ReservableRoom reservableRoom = new ReservableRoom(reservableRoomId);
        Reservation reservation1 = new Reservation(LocalTime.of(11, 0), LocalTime.of(12, 0), reservableRoom, null);
        Reservation reservation2 = new Reservation(LocalTime.of(11, 0), LocalTime.of(12, 0), reservableRoom, null);
        assertTrue(reservation1.overlap(reservation2));
    }

    @Test
    @DisplayName("同じ部屋の予約で開始時刻が同じで終了時刻が異なる場合は重複する")
    void testOverlapWithSameRoomIdAndOverlappingTimes() {
        ReservableRoomId reservableRoomId = new ReservableRoomId(1, null);
        ReservableRoom reservableRoom = new ReservableRoom(reservableRoomId);
        Reservation reservation1 = new Reservation(LocalTime.of(11, 0), LocalTime.of(13, 0), reservableRoom, null);
        Reservation reservation2 = new Reservation(LocalTime.of(12, 0), LocalTime.of(14, 0), reservableRoom, null);
        assertTrue(reservation1.overlap(reservation2));
    }

    @Test
    @DisplayName("同じ部屋の予約で開始時刻が異なり終了時刻が同じ場合は重複する")
    void testNoOverlapWithSameRoomIdAndNonOverlappingTimes() {
        ReservableRoomId reservableRoomId = new ReservableRoomId(1, null);
        ReservableRoom reservableRoom = new ReservableRoom(reservableRoomId);
        Reservation reservation1 = new Reservation(LocalTime.of(11, 0), LocalTime.of(13, 0), reservableRoom, null);
        Reservation reservation2 = new Reservation(LocalTime.of(13, 1), LocalTime.of(14, 0), reservableRoom, null);
        assertFalse(reservation1.overlap(reservation2));
    }

}
