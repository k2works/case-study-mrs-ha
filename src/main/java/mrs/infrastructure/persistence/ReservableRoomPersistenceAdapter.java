package mrs.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import mrs.domain.model.ReservableRoom;
import mrs.domain.model.ReservableRoomId;
import mrs.infrastructure.repository.room.ReservableRoomRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservableRoomPersistenceAdapter {
    private final ReservableRoomRepository reservableRoomRepository;
    private final ReservableRoomMapper reservableRoomMapper;

    public ReservableRoom findById(ReservableRoomId roomId) {
        ReservableRoomIdJpaEntity reservableRoomIdJpaEntity = new ReservableRoomIdJpaEntity(roomId.getRoomId(), roomId.getReservedDate());
        return reservableRoomMapper.mapToDomainEntity(reservableRoomRepository.findById(reservableRoomIdJpaEntity).orElseThrow());
    }

    public void save(ReservableRoom room) {
        reservableRoomRepository.save(reservableRoomMapper.mapToJpaEntity(room));
    }

    public List<ReservableRoom> findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(LocalDate date) {
        return reservableRoomMapper.mapToDomainEntityList(reservableRoomRepository.findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(date));
    }

    public ReservableRoom findOneForUpdateByReservableRoomId(ReservableRoomId reservableRoomId) {
        ReservableRoomIdJpaEntity reservableRoomIdJpaEntity = new ReservableRoomIdJpaEntity(reservableRoomId.getRoomId(), reservableRoomId.getReservedDate());
        return reservableRoomMapper.mapToDomainEntity(reservableRoomRepository.findOneForUpdateByReservableRoomId(reservableRoomIdJpaEntity));
    }

    public void deleteAll() {
        reservableRoomRepository.deleteAll();
    }
}
