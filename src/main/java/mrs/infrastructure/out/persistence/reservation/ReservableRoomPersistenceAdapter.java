package mrs.infrastructure.out.persistence.reservation;

import lombok.RequiredArgsConstructor;
import mrs.application.domain.model.reservation.ReservableRoom;
import mrs.application.domain.model.reservation.ReservableRoomId;
import mrs.application.port.out.ReservableRoomPort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservableRoomPersistenceAdapter implements ReservableRoomPort {
    private final ReservableRoomRepository reservableRoomRepository;
    private final ReservableRoomMapper reservableRoomMapper;

    public ReservableRoom findById(ReservableRoomId roomId) {
        ReservableRoomIdJpaEntity reservableRoomIdJpaEntity = new ReservableRoomIdJpaEntity(roomId.getRoomId().getValue(), roomId.getReservedDate().getValue());
        return reservableRoomMapper.mapToDomainEntity(reservableRoomRepository.findById(reservableRoomIdJpaEntity).orElseThrow());
    }

    public void save(ReservableRoom room) {
        reservableRoomRepository.save(reservableRoomMapper.mapToJpaEntity(room));
    }

    public List<ReservableRoom> findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(LocalDate date) {
        return reservableRoomMapper.mapToDomainEntityList(reservableRoomRepository.findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(date));
    }

    public ReservableRoom findOneForUpdateByReservableRoomId(ReservableRoomId reservableRoomId) {
        ReservableRoomIdJpaEntity reservableRoomIdJpaEntity = new ReservableRoomIdJpaEntity(reservableRoomId.getRoomId().getValue(), reservableRoomId.getReservedDate().getValue());
        return reservableRoomMapper.mapToDomainEntity(reservableRoomRepository.findOneForUpdateByReservableRoomId(reservableRoomIdJpaEntity));
    }

    public void deleteAll() {
        reservableRoomRepository.deleteAll();
    }
}
