package mrs.presentation.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Data
public class ReservationForm {
    @NotNull(message = "必須です")
    @ThirtyMinutesUnit(message = "30分単位で入力してください")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @NotNull(message = "必須です")
    @ThirtyMinutesUnit(message = "30分単位で入力してください")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;
}
