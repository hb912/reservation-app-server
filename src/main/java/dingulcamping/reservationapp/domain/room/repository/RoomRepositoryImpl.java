package dingulcamping.reservationapp.domain.room.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dingulcamping.reservationapp.domain.room.dto.QRoomInfoDto;
import dingulcamping.reservationapp.domain.room.dto.RoomInfoDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static dingulcamping.reservationapp.domain.room.entity.QRoom.room;

@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<RoomInfoDto> findInfoAll() {
        return queryFactory
                .select(new QRoomInfoDto(
                        room.id,
                        room.name,
                        room.price,
                        room.roomType,
                        room.icon,
                        room.maxPeople,
                        room.minPeople,
                        room.mapPosition.as("position")
                        ))
                .from(room)
                .fetch();
    }
}
