package mrs.domain.repository.room;

import mrs.domain.model.MeetingRoom;
import mrs.domain.model.ReservableRoom;
import mrs.domain.model.ReservableRoomId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class ReservableRoomRepositoryTest {

    @Autowired
    ReservableRoomRepository reservableRoomRepository;

    @Autowired
    MeetingRoomRepository meetingRoomRepository;

    @BeforeEach
    void setUp() {
        reservableRoomRepository.deleteAll();

        ReservableRoomId id1 = new ReservableRoomId(1, LocalDate.of(2023, 10, 1));
        ReservableRoomId id2 = new ReservableRoomId(2, LocalDate.of(2023, 10, 1));

        MeetingRoom room1 = new MeetingRoom();
        room1.setRoomId(1);
        room1.setRoomName("会議室1");
        meetingRoomRepository.save(room1);

        MeetingRoom room2 = new MeetingRoom();
        room2.setRoomId(2);
        room2.setRoomName("会議室2");
        meetingRoomRepository.save(room2);

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