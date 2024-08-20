package mrs.infrastructure.out.persistence.reservation;

import mrs.application.domain.model.auth.*;
import mrs.application.domain.model.reservation.ReservableRoom;
import mrs.application.domain.model.reservation.ReservableRoomId;
import mrs.application.domain.model.reservation.Reservation;
import mrs.application.domain.model.room.MeetingRoom;
import mrs.infrastructure.out.persistence.room.MeetingMeetingRoomPersistenceAdapter;
import mrs.infrastructure.out.persistence.room.MeetingRoomMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({ReservationPersistenceAdapter.class, ReservationMapper.class, ReservableRoomPersistenceAdapter.class, ReservableRoomMapper.class, MeetingMeetingRoomPersistenceAdapter.class, MeetingRoomMapper.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReservationPersistenceAdapterTest {

    @Autowired
    MeetingMeetingRoomPersistenceAdapter meetingRoomRepository;
    @Autowired
    ReservableRoomPersistenceAdapter reservableRoomRepository;
    @Autowired
    ReservationPersistenceAdapter reservationRepository;

    @BeforeEach
    void setUp() {
        reservableRoomRepository.deleteAll();

        ReservableRoomId id1 = new ReservableRoomId(1, LocalDate.of(2023, 10, 1));

        MeetingRoom room1 = MeetingRoom.of(1, "会議室1");
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

        User user = new User(new UserId("taro-yamada"), new Password("$2a$10$oxSJl.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK"), new Name("太郎", "山田"), RoleName.USER);
        Reservation reservation = Reservation.of(1, LocalTime.from(reservedDate.atTime(10, 0)), LocalTime.from(reservedDate.atTime(11, 0)), room, user);
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
