package mrs.application.service.room;

import mrs.application.domain.model.reservation.ReservableRoom;
import mrs.application.domain.model.reservation.ReservableRoomId;
import mrs.application.domain.model.reservation.ReservableRoomList;
import mrs.application.domain.model.reservation.ReservedDate;
import mrs.application.domain.model.room.MeetingRoom;
import mrs.application.domain.model.room.RoomId;
import mrs.infrastructure.out.persistence.reservation.ReservableRoomPersistenceAdapter;
import mrs.infrastructure.out.persistence.room.MeetingMeetingRoomPersistenceAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RoomServiceTest {

    @Mock
    private ReservableRoomPersistenceAdapter reservableRoomRepository;
    @Mock
    private MeetingMeetingRoomPersistenceAdapter meetingRoomRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    public void setUp() {
        ReservableRoomId roomId = ReservableRoomId.of(1, LocalDate.now());
        List<ReservableRoom> rooms = new ArrayList<>();
        rooms.add(new ReservableRoom(roomId));
        when(reservableRoomRepository.findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(any())).thenReturn(rooms);
    }

    @Test
    @DisplayName("本日の日付の予約可能会議室一覧を取得する")
    public void testFindReservableRooms() {
        LocalDate date = LocalDate.now();
        ReservableRoomList reservableRooms = roomService.findReservableRooms(new ReservedDate(date));
        assertNotNull(reservableRooms, "Returned list should not be null");
        assertEquals(1, reservableRooms.size(), "The size of the returned list should be 1");
        assertEquals(1, reservableRooms.getValue().get(0).getReservableRoomId().getRoomId().getValue(), "The roomId should be 1");
        assertEquals(date, reservableRooms.getValue().get(0).getReservableRoomId().getReservedDate().getValue(), "Reserved date should be equal");
    }

    @Test
    @DisplayName("会議室が見つかる時")
    public void testFindMeetingRoom_Found() {
        RoomId roomId = new RoomId(1);
        MeetingRoom expectedMeetingRoom = MeetingRoom.of(roomId.getValue(), "会議室1");
        when(meetingRoomRepository.findById(roomId.getValue())).thenReturn(expectedMeetingRoom);

        MeetingRoom meetingRoom = roomService.findMeetingRoom(roomId);
        assertNotNull(meetingRoom, "Returned meeting room should not be null");
        assertEquals(roomId, meetingRoom.getRoomId(), "Returned room id should be 1");
    }

    @Test
    @DisplayName("会議室が見つからない時")
    public void testFindMeetingRoom_NotFound() {
        RoomId roomId = new RoomId(1);
        when(meetingRoomRepository.findById(roomId.getValue())).thenReturn(null);

        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            roomService.findMeetingRoom(roomId);
        });
        assertEquals("会議室が見つかりません。", exception.getMessage());
    }
}
