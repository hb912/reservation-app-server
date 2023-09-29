package dingulcamping.reservationapp.domain.room.controller;

import dingulcamping.reservationapp.domain.room.dto.RoomCreateDto;
import dingulcamping.reservationapp.domain.room.dto.RoomDetailDto;
import dingulcamping.reservationapp.domain.room.dto.RoomInfoDto;
import dingulcamping.reservationapp.domain.room.entity.Room;
import dingulcamping.reservationapp.domain.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/room")
@Slf4j
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDetailDto> roomDetail(@PathVariable("roomId") Long roomId){
        RoomDetailDto roomDetail = roomService.getRoomDetail(Long.valueOf(roomId));
        return ResponseEntity.ok(roomDetail);
    }

    @GetMapping("")
    public ResponseEntity<List<RoomInfoDto>> getRooms(){
        List<RoomInfoDto> allRooms = roomService.getAllRooms();
        return ResponseEntity.ok(allRooms);
    }

    @PostMapping("/create")
    public ResponseEntity<String> getRooms(@RequestBody RoomCreateDto roomCreateDto){
        roomService.createRoom(roomCreateDto);
        return ResponseEntity.ok(roomCreateDto.getName()+"등록 완료");
    }
}
