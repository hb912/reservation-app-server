package dingulcamping.reservationapp.domain.room.repository;

import dingulcamping.reservationapp.domain.room.dto.SimpleRoomDto;
import dingulcamping.reservationapp.domain.room.dto.RoomInfoDto;

import java.util.List;

public interface RoomRepositoryCustom {
    List<RoomInfoDto> findInfoAll();
    List<SimpleRoomDto> findDisableRoomByPeopleNumber(int peopleNumber);
}
