package mrs.app.reservation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationFormTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    @DisplayName("正しい予約フォーム")
    public void testValidReservationForm() {
        ReservationForm form = new ReservationForm();
        form.setStartTime(LocalTime.of(10, 0));
        form.setEndTime(LocalTime.of(11, 0));

        Set<ConstraintViolation<ReservationForm>> violations = validator.validate(form);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("開始時刻がnull")
    public void testNullStartTime() {
        ReservationForm form = new ReservationForm();
        form.setStartTime(null);
        form.setEndTime(LocalTime.of(11, 0));

        Set<ConstraintViolation<ReservationForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());

        for (ConstraintViolation<ReservationForm> violation : violations) {
            assertEquals("必須です", violation.getMessage());
        }
    }

    @Test
    @DisplayName("開始時刻が不正")
    public void testInvalidStartTime() {
        ReservationForm form = new ReservationForm();
        form.setStartTime(LocalTime.of(10, 15)); // 30分単位に合致しない
        form.setEndTime(LocalTime.of(11, 0));

        Set<ConstraintViolation<ReservationForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());

        for (ConstraintViolation<ReservationForm> violation : violations) {
            if ("startTime".equals(violation.getPropertyPath().toString())) {
                assertEquals("30分単位で入力してください", violation.getMessage());
            }
        }
    }

    @Test
    @DisplayName("終了時刻がnull")
    public void testNullEndTime() {
        ReservationForm form = new ReservationForm();
        form.setStartTime(LocalTime.of(10, 0));
        form.setEndTime(null);

        Set<ConstraintViolation<ReservationForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());

        for (ConstraintViolation<ReservationForm> violation : violations) {
            assertEquals("必須です", violation.getMessage());
        }
    }

    @Test
    @DisplayName("終了時刻が不正")
    public void testInvalidEndTime() {
        ReservationForm form = new ReservationForm();
        form.setStartTime(LocalTime.of(10, 0));
        form.setEndTime(LocalTime.of(11, 15)); // 30分単位に合致しない

        Set<ConstraintViolation<ReservationForm>> violations = validator.validate(form);
        assertFalse(violations.isEmpty());

        for (ConstraintViolation<ReservationForm> violation : violations) {
            if ("endTime".equals(violation.getPropertyPath().toString())) {
                assertEquals("30分単位で入力してください", violation.getMessage());
            }
        }
    }
}
