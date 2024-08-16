package mrs.app.reservation;

import mrs.domain.model.*;
import mrs.domain.service.reservation.ReservationService;
import mrs.domain.service.reservation.UnavailableReservationException;
import mrs.domain.service.room.RoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationsController.class)
public class ReservationsControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    RoomService roomService;
    @MockBean
    ReservationService reservationService;

    @Test
    @DisplayName("指定した日付と会議室IDに対応する予約情報を取得して予約フォームを表示する")
    public void reserveForm() throws Exception {
        Integer roomId = 1;
        LocalDate date = LocalDate.of(2022, 2, 22);
        MeetingRoom meetingRoom = new MeetingRoom(roomId, "Room-1");
        ReservableRoomId reservableRoomId = new ReservableRoomId(roomId, date);
        ReservableRoom reservableRoom = new ReservableRoom(reservableRoomId, meetingRoom);
        ReservationForm form = new ReservationForm();
        form.setStartTime(LocalTime.of(9, 0));
        form.setEndTime(LocalTime.of(10, 0));
        User user = new User();
        user.setUserId("taro-yamada");
        user.setFirstName("太郎");
        user.setLastName("山田");
        user.setRoleName(RoleName.USER);
        Reservation reservation = new Reservation(1, form.getStartTime(), form.getEndTime(), reservableRoom, user);
        LocalTime time1 = LocalTime.of(0, 0);
        LocalTime time2 = LocalTime.of(0, 30);
        given(roomService.findMeetingRoom(roomId)).willReturn(meetingRoom);
        given(reservationService.findReservations(reservableRoomId)).willReturn(List.of(reservation));
        this.mockMvc.perform(get("/reservations/{date}/{roomId}", date, roomId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("room"))
                .andExpect(model().attributeExists("reservations"))
                .andExpect(model().attribute("room", meetingRoom))
                .andExpect(model().attribute("reservations", List.of(reservation)))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @DisplayName("BindResultにエラーがある場合、エラー付きでreserveFormを返すことをテスト")
    public void testReserve_BindResultHasErrors() throws Exception {
        Integer roomId = 1;
        LocalDate date = LocalDate.of(2022, 2, 22);
        MeetingRoom meetingRoom = new MeetingRoom(roomId, "Room-1");
        ReservableRoomId reservableRoomId = new ReservableRoomId(roomId, date);
        ReservableRoom reservableRoom = new ReservableRoom(reservableRoomId, meetingRoom);
        ReservationForm form = new ReservationForm();
        form.setStartTime(null);
        form.setEndTime(LocalTime.of(10, 0));
        given(roomService.findMeetingRoom(roomId)).willReturn(meetingRoom);
        mockMvc.perform(post("/reservations/{date}/{roomId}", date, roomId)
                        .flashAttr("reservationForm", form))
                .andExpect(status().isOk())
                .andExpect(view().name("reservation/reserveForm"));
    }

    @Test
    @DisplayName("予約中に例外が発生した場合に、エラーメッセージ付きでreserveFormを返すことをテスト")
    public void testReserve_ThrowsExceptionDuringReservation() throws Exception {
        Integer roomId = 1;
        LocalDate date = LocalDate.of(2022, 2, 22);
        MeetingRoom meetingRoom = new MeetingRoom(roomId, "Room-1");
        ReservableRoomId reservableRoomId = new ReservableRoomId(roomId, date);
        ReservableRoom reservableRoom = new ReservableRoom(reservableRoomId, meetingRoom);
        ReservationForm form = new ReservationForm();
        form.setStartTime(LocalTime.of(9, 0));
        form.setEndTime(LocalTime.of(10, 0));
        doThrow(new UnavailableReservationException("nothing")).when(reservationService).reserve(any(Reservation.class));
        given(roomService.findMeetingRoom(roomId)).willReturn(meetingRoom);
        mockMvc.perform(post("/reservations/{date}/{roomId}", date, roomId)
                        .flashAttr("reservationForm", form))
                .andExpect(status().isOk())
                .andExpect(view().name("reservation/reserveForm"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    @DisplayName("エラーや例外が発生しない場合に、/reservations/{date}/{roomId}にリダイレクトすることをテスト")
    public void testReserve_NoErrorsOrExceptions() throws Exception {
        Integer roomId = 1;
        LocalDate date = LocalDate.of(2022, 2, 22);
        MeetingRoom meetingRoom = new MeetingRoom(roomId, "Room-1");
        ReservableRoomId reservableRoomId = new ReservableRoomId(roomId, date);
        ReservableRoom reservableRoom = new ReservableRoom(reservableRoomId, meetingRoom);
        ReservationForm form = new ReservationForm();
        form.setStartTime(LocalTime.of(9, 0));
        form.setEndTime(LocalTime.of(10, 0));
        mockMvc.perform(post("/reservations/{date}/{roomId}", date, roomId)
                        .flashAttr("reservationForm", form))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/reservations/" + date + "/" + roomId));
    }

    @Test
    @DisplayName("存在しない予約をキャンセルしようとした場合、エラーメッセージと共にフォームが再読み込みされるべきである")
    public void testCancel_NonExistentReservation() throws Exception {
        Integer roomId = 1;
        Integer reservationId = 1;
        LocalDate date = LocalDate.of(2022, 2, 22);
        String errorMessage = "Reservation does not exist.";
        doThrow(new IllegalStateException(errorMessage)).when(reservationService).cancel(eq(reservationId), any(User.class));
        MeetingRoom meetingRoom = new MeetingRoom(roomId, "Room-1");
        given(roomService.findMeetingRoom(roomId)).willReturn(meetingRoom);
        this.mockMvc.perform(post("/reservations/{date}/{roomId}", date, roomId).param("cancel", "")
                        .param("reservationId", String.valueOf(reservationId)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", errorMessage));
    }

    @Test
    @DisplayName("予約のキャンセルが成功した場合、ページは更新された予約一覧にリダイレクトされるべきである")
    public void testCancel_SuccessfulCancellation() throws Exception {
        Integer roomId = 1;
        Integer reservationId = 1;
        LocalDate date = LocalDate.of(2022, 2, 22);
        this.mockMvc.perform(post("/reservations/{date}/{roomId}", date, roomId).param("cancel", "")
                        .param("reservationId", String.valueOf(reservationId)))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/reservations/" + date + "/" + roomId));
    }
}
