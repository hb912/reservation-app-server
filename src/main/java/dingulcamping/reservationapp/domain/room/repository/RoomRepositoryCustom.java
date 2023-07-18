package dingulcamping.reservationapp.domain.room.repository;

import dingulcamping.reservationapp.domain.room.dto.RoomDetailDto;
import dingulcamping.reservationapp.domain.room.dto.RoomInfoDto;

import java.util.List;
import java.util.Optional;

public interface RoomRepositoryCustom {
    List<RoomInfoDto> findInfoAll();
}
