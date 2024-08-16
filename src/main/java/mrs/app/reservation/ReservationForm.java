package mrs.app.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Data
public class ReservationForm {
    @NotNull(message = "必須です")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @NotNull(message = "必須です")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;
}
