package mrs.application.service.reservation;

import mrs.application.domain.model.auth.*;
import mrs.application.domain.model.reservation.ReservableRoom;
import mrs.application.domain.model.reservation.ReservableRoomId;
import mrs.application.domain.model.reservation.Reservation;
import mrs.application.domain.model.room.MeetingRoom;
import mrs.infrastructure.out.persistence.reservation.ReservableRoomPersistenceAdapter;
import mrs.infrastructure.out.persistence.reservation.ReservationPersistenceAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DataJpaTest
public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationPersistenceAdapter reservationRepository;

    @Mock
    private ReservableRoomPersistenceAdapter reservableRoomRepository;

    @Test
    @DisplayName("指定した部屋IDの予約情報を取得する")
    public void shouldFindReservationsByRoomId() {
        ReservableRoomId reservableRoomId = new ReservableRoomId(1, LocalDate.of(2023, 10, 1));
        List<Reservation> expectedReservations = IntStream.range(0, 5)
                .mapToObj(i -> new Reservation(/* construct as needed */))
                .collect(Collectors.toList());

        when(reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId)).
                thenReturn(expectedReservations);

        List<Reservation> actualReservations = reservationService.findReservations(reservableRoomId);

        assertEquals(expectedReservations, actualReservations);
    }

    @Test
    @DisplayName("指定した部屋IDの予約情報が見つからない場合、空のリストを返す")
    public void shouldReturnEmptyListIfNoReservationsFoundByRoomId() {
        ReservableRoomId reservableRoomId = new ReservableRoomId(1, LocalDate.of(2023, 10, 1));

        when(reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId)).
                thenReturn(List.of());

        List<Reservation> actualReservations = reservationService.findReservations(reservableRoomId);

        assertEquals(List.of(), actualReservations);
    }

    @Test
    @DisplayName("正常に予約に成功する場合")
    public void shouldSuccessfullyReserve() {
        ReservableRoomId reservableRoomId = new ReservableRoomId();
        ReservableRoom reservableRoom = new ReservableRoom(reservableRoomId, new MeetingRoom(reservableRoomId.getRoomId(), "会議室1"));
        Reservation reservation = new Reservation(LocalTime.of(10, 0), LocalTime.of(11, 0), reservableRoom, null);

        when(reservableRoomRepository.findOneForUpdateByReservableRoomId(reservableRoomId)).thenReturn(reservableRoom);
        when(reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId))
                .thenReturn(new ArrayList<>());

        Reservation actualReservation = reservationService.reserve(reservation);

        assertEquals(reservation, actualReservation);
    }

    @Test
    @DisplayName("会議室が存在しない場合はエラーが発生する")
    public void shouldThrowErrorWhenRoomDoesNotExist() {
        ReservableRoomId reservableRoomId = new ReservableRoomId();
        ReservableRoom reservableRoom = new ReservableRoom(reservableRoomId, new MeetingRoom(reservableRoomId.getRoomId(), "会議室1"));
        Reservation reservation = new Reservation(LocalTime.of(10, 0), LocalTime.of(11, 0), reservableRoom, null);

        when(reservableRoomRepository.findById(reservableRoomId)).thenReturn(null);

        assertThrows(UnavailableReservationException.class, () -> {
            reservationService.reserve(reservation);
        });
    }

    @Test
    @DisplayName("他の予約と時間が重複する場合はエラーが発生する")
    public void shouldThrowErrorWhenReservationOverlaps() {
        ReservableRoomId reservableRoomId = new ReservableRoomId(1, LocalDate.of(2023, 10, 1));
        MeetingRoom room = new MeetingRoom(1, "会議室1");
        ReservableRoom reservableRoom = new ReservableRoom(reservableRoomId, room);
        Reservation reservation = new Reservation(LocalTime.of(10, 0), LocalTime.of(11, 0), reservableRoom, null);

        when(reservableRoomRepository.findOneForUpdateByReservableRoomId(reservableRoomId)).thenReturn(reservableRoom);
        when(reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId))
                .thenReturn(List.of(reservation));

        assertThrows(AlreadyReservedException.class, () -> {
            reservationService.reserve(reservation);
        });
    }

    @Test
    @DisplayName("正常に予約をキャンセルする場合")
    public void shouldSuccessfullyCancel() {
        int reservationId = 1;
        User requestUser = new User(new UserId("admin"), new Password("password"), new Name("太郎", "山田"), RoleName.ADMIN);
        Reservation reservation = new Reservation(reservationId, LocalTime.of(10, 0), LocalTime.of(11, 0), null, requestUser);

        when(reservationRepository.findById(reservationId)).thenReturn(reservation);

        reservationService.cancel(reservation);
        verify(reservationRepository, times(1)).delete(reservation);
    }

    @Test
    @DisplayName("予約が存在しない場合はエラーが発生する")
    public void shouldThrowErrorWhenReservationDoesNotExist() {
        int reservationId = 1;

        when(reservationRepository.findById(reservationId)).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> {
            reservationService.findOne(reservationId);
        });
    }

    @Test
    @DisplayName("管理者権限を持つユーザーが他のユーザーの予約をキャンセルする場合")
    public void shouldSuccessfullyCancelOtherUsersReservation() {
        int reservationId = 1;
        User owner = new User(new UserId("user1"), new Password("password"), new Name("太郎", "山田"), RoleName.USER);
        Reservation reservation = new Reservation(reservationId, LocalTime.of(10, 0), LocalTime.of(11, 0), null, owner);

        when(reservationRepository.findById(reservationId)).thenReturn(reservation);

        reservationService.cancel(reservation);
        verify(reservationRepository, times(1)).delete(reservation);
    }
}
