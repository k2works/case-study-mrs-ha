package mrs.infrastructure.in.web.reservation;

import lombok.RequiredArgsConstructor;
import mrs.application.domain.model.reservation.*;
import mrs.application.domain.model.room.MeetingRoom;
import mrs.application.domain.model.room.RoomId;
import mrs.application.port.in.ReservationUseCase;
import mrs.application.port.in.RoomUseCase;
import mrs.application.service.auth.AuthUserDetails;
import mrs.application.service.reservation.AlreadyReservedException;
import mrs.application.service.reservation.UnavailableReservationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


/**
 * 会議室予約画面
 */
@Controller
@RequestMapping("reservations/{date}/{roomId}")
@RequiredArgsConstructor
public class ReservationsController {
    private final RoomUseCase roomUseCase;
    private final ReservationUseCase reservationUseCase;

    @ModelAttribute
    ReservationForm setUpForm() {
        ReservationForm form = new ReservationForm();
        form.setStartTime(LocalTime.of(9, 0));
        form.setEndTime(LocalTime.of(10, 0));
        return form;
    }

    /**
     * 指定した会議室の予約画面
     */
    @RequestMapping(method = RequestMethod.GET)
    String reserveForm(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date, @PathVariable("roomId") Integer roomId, Model model) {
        MeetingRoom meetingRoom = roomUseCase.findMeetingRoom(new RoomId(roomId));
        ReservableRoomId reservableRoomId = ReservableRoomId.of(roomId, date);
        ReservationList reservations = reservationUseCase.findReservations(reservableRoomId);

        List<LocalTime> timeList = Stream.iterate(LocalTime.of(0, 0), t -> t.plusMinutes(30)).limit(24 * 2).toList();
        List<Reservation> reservationList = reservations != null ? reservations.getValue() : Collections.emptyList();

        model.addAttribute("room", meetingRoom);
        model.addAttribute("reservations", reservationList);
        model.addAttribute("timeList", timeList);
        return "reservation/reserveForm";
    }

    /**
     * 指定した会議室の予約処理
     */
    @RequestMapping(method = RequestMethod.POST)
    String reserve(@Validated ReservationForm form, BindingResult bindingResult, @AuthenticationPrincipal AuthUserDetails userDetails, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date, @PathVariable("roomId") Integer roomId, Model model) {
        if (bindingResult.hasErrors()) {
            return reserveForm(date, roomId, model);
        }

        ReservableRoom reservableRoom = new ReservableRoom(ReservableRoomId.of(roomId, date));
        Reservation reservation = new Reservation(form.getStartTime(), form.getEndTime(), reservableRoom, userDetails.getUser());

        try {
            reservationUseCase.reserve(reservation);
        } catch (UnavailableReservationException | AlreadyReservedException e) {
            model.addAttribute("error", e.getMessage());
            return reserveForm(date, roomId, model);
        }

        return "redirect:/reservations/{date}/{roomId}";
    }

    /**
     * 指定した会議室の予約取消処理
     */
    @RequestMapping(method = RequestMethod.POST, params = "cancel")
    String cancel(@RequestParam("reservationId") Integer reservationId, @PathVariable("roomId") Integer roomId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date, Model model) {
        try {
            Reservation reservation = reservationUseCase.findOne(new ReservationId(reservationId));
            reservationUseCase.cancel(reservation);
        } catch (AccessDeniedException e) {
            model.addAttribute("error", e.getMessage());
            return reserveForm(date, roomId, model);
        }
        return "redirect:/reservations/{date}/{roomId}";
    }
}
