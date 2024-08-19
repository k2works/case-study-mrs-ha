package mrs.infrastructure.persistence;

import mrs.domain.model.MeetingRoom;
import mrs.domain.model.ReservableRoom;
import mrs.domain.model.ReservableRoomId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({ReservableRoomPersistenceAdapter.class, ReservableRoomMapper.class, MeetingRoomPersistenceAdapter.class, MeetingRoomMapper.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReservableRoomPersistenceAdapterTest {

    @Autowired
    ReservableRoomPersistenceAdapter reservableRoomRepository;

    @Autowired
    MeetingRoomPersistenceAdapter meetingRoomRepository;

    @BeforeEach
    void setUp() {
        reservableRoomRepository.deleteAll();

        MeetingRoom room1 = new MeetingRoom(1, "会議室1");
        meetingRoomRepository.save(room1);

        MeetingRoom room2 = new MeetingRoom(2, "会議室2");
        meetingRoomRepository.save(room2);

        ReservableRoomId id1 = new ReservableRoomId(1, LocalDate.of(2023, 10, 1));
        ReservableRoomId id2 = new ReservableRoomId(2, LocalDate.of(2023, 10, 1));
        ReservableRoom reservableRoom1 = new ReservableRoom(id1, room1);
        ReservableRoom reservableRoom2 = new ReservableRoom(id2, room2);

        reservableRoomRepository.save(reservableRoom1);
        reservableRoomRepository.save(reservableRoom2);
    }

    @Test
    @DisplayName("特定の日付の予約可能会議室を取得する")
    void testFindByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc() {
        LocalDate reservedDate = LocalDate.of(2023, 10, 1);
        List<ReservableRoom> rooms = reservableRoomRepository.findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(reservedDate);

        assertThat(rooms).hasSize(2);
        assertThat(rooms.get(0).getReservableRoomId().getRoomId()).isEqualTo(1);
        assertThat(rooms.get(1).getReservableRoomId().getRoomId()).isEqualTo(2);
    }
}
