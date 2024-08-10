package mrs.domain.service.room;

import mrs.domain.model.ReservableRoom;
import mrs.domain.model.ReservableRoomId;
import mrs.domain.repository.room.ReservableRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RoomServiceTest {

    @Mock
    private ReservableRoomRepository reservableRoomRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    public void setUp() {
        ReservableRoomId roomId = new ReservableRoomId(1, LocalDate.now());
        List<ReservableRoom> rooms = new ArrayList<>();
        rooms.add(new ReservableRoom(roomId));
        when(reservableRoomRepository.findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(any())).thenReturn(rooms);
    }

    @Test
    @DisplayName("本日の日付の予約可能会議室一覧を取得する")
    public void testFindReservableRooms() {
        LocalDate date = LocalDate.now();
        List<ReservableRoom> reservableRooms = roomService.findReservableRooms(date);
        assertNotNull(reservableRooms, "Returned list should not be null");
        assertEquals(1, reservableRooms.size(), "The size of the returned list should be 1");
        assertEquals(1, reservableRooms.get(0).getReservableRoomId().getRoomId(), "The roomId should be 1");
        assertEquals(date, reservableRooms.get(0).getReservableRoomId().getReservedDate(), "Reserved date should be equal");
    }
}