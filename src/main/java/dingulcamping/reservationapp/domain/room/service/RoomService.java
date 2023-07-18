package dingulcamping.reservationapp.domain.room.service;

import dingulcamping.reservationapp.domain.room.dto.RoomCreateDto;
import dingulcamping.reservationapp.domain.room.dto.RoomDetailDto;
import dingulcamping.reservationapp.domain.room.dto.RoomInfoDto;
import dingulcamping.reservationapp.domain.room.entity.Room;
import dingulcamping.reservationapp.domain.room.exception.ExistSameNameRoomException;
import dingulcamping.reservationapp.domain.room.exception.NotExistRoomException;
import dingulcamping.reservationapp.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomDetailDto getRoomDetail(Long roomId){
        Optional<Room> findById = roomRepository.findById(roomId);
        if(findById.isEmpty()){
            throw new NotExistRoomException("존재하지 않는 방입니다.");
        }
        RoomDetailDto roomDetailDto = new RoomDetailDto(findById.get());
        return roomDetailDto;
    }

}
