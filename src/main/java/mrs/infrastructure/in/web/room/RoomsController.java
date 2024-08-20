package mrs.infrastructure.in.web.room;

import lombok.RequiredArgsConstructor;
import mrs.application.domain.model.reservation.ReservableRoom;
import mrs.application.port.in.RoomUseCase;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.List;

/**
 * 会議室一覧表示画面
 */
@Controller
@RequestMapping("rooms")
@RequiredArgsConstructor
public class RoomsController {
    private final RoomUseCase roomUseCase;

    /**
     * 今日の予約可能会議室一覧
     */
    @RequestMapping(method = RequestMethod.GET)
    String listRooms(Model model) {
        LocalDate today = LocalDate.now();
        List<ReservableRoom> rooms = roomUseCase.findReservableRooms(today);
        model.addAttribute("date", today);
        model.addAttribute("rooms", rooms);
        return "room/listRooms";
    }

    /**
     * 特定日付の予約可能会議室一覧
     */
    @RequestMapping(path = "{date}", method = RequestMethod.GET)
    String listRooms(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date, Model model) {
        List<ReservableRoom> rooms = roomUseCase.findReservableRooms(date);
        model.addAttribute("rooms", rooms);
        return "room/listRooms";
    }
}
