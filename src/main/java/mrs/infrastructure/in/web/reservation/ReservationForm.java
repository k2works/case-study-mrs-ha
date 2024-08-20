package mrs.infrastructure.in.web.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mrs.common.validation.EndTimeMustBeAfterStartTime;
import mrs.common.validation.ThirtyMinutesUnit;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalTime;

@Data
@EndTimeMustBeAfterStartTime(message = "終了時刻は開始時刻より後にしてください")
public class ReservationForm implements Serializable {
    @NotNull(message = "必須です")
    @ThirtyMinutesUnit(message = "30分単位で入力してください")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @NotNull(message = "必須です")
    @ThirtyMinutesUnit(message = "30分単位で入力してください")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;
}
