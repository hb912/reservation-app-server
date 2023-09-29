package dingulcamping.reservationapp.domain.room.repository;

import dingulcamping.reservationapp.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Long>,RoomRepositoryCustom {
    Optional<Room> findOneByName(String name);
}
