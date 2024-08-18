package mrs.service.reservation;

public class UnavailableReservationException extends RuntimeException {
    public UnavailableReservationException(String message) {
        super(message);
    }
}
