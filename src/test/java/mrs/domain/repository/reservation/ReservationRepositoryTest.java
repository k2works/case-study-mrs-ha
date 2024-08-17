package mrs.domain.repository.reservation;

import mrs.domain.model.*;
import mrs.domain.repository.room.MeetingRoomRepository;
import mrs.domain.repository.room.ReservableRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReservationRepositoryTest {

    @Autowired
    MeetingRoomRepository meetingRoomRepository;
    @Autowired
    ReservableRoomRepository reservableRoomRepository;
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        reservableRoomRepository.deleteAll();

        ReservableRoomId id1 = new ReservableRoomId(1, LocalDate.of(2023, 10, 1));

        MeetingRoom room1 = new MeetingRoom();
        room1.setRoomId(1);
        room1.setRoomName("会議室1");
        meetingRoomRepository.save(room1);

        ReservableRoom reservableRoom1 = new ReservableRoom(id1, room1);

        reservableRoomRepository.save(reservableRoom1);
    }

    @Test
    @DisplayName("指定した日付の予約が存在する場合、予約情報を取得する")
    void findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc_Found() {
        LocalDate reservedDate = LocalDate.of(2023, 10, 1);
        List<ReservableRoom> rooms = reservableRoomRepository.findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(reservedDate);
        ReservableRoom room = rooms.get(0);

        User user = new User();
        user.setUserId("taro-yamada");
        user.setFirstName("太郎");
        user.setLastName("山田");
        user.setRoleName(RoleName.USER);

        Reservation reservation = new Reservation();
        reservation.setStartTime(LocalTime.from(reservedDate.atTime(10, 0)));
        reservation.setEndTime(LocalTime.from(reservedDate.atTime(11, 0)));
        reservation.setReservableRoom(room);
        reservation.setUser(user);
        reservationRepository.save(reservation);

        List<Reservation> foundReservations = reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(room.getReservableRoomId());

        assertThat(foundReservations).isNotEmpty();
        assertThat(foundReservations.get(0)).isEqualTo(reservation);
    }

    @Test
    @DisplayName("指定した日付の予約が存在しない場合、空のリストを取得する")
    void findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc_Empty() {
        ReservableRoomId reservableRoomId = new ReservableRoomId();

        List<Reservation> foundReservations = reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId);

        assertThat(foundReservations).isEmpty();
    }
}
