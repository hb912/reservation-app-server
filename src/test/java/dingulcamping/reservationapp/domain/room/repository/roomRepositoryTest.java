package dingulcamping.reservationapp.domain.room.repository;

import dingulcamping.reservationapp.domain.room.entity.MapPosition;
import dingulcamping.reservationapp.domain.room.entity.Room;
import dingulcamping.reservationapp.domain.room.entity.RoomType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static dingulcamping.reservationapp.domain.room.entity.RoomType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class roomRepositoryTest {

    @Autowired RoomRepository roomRepository;


    @Test
    public void createRoomAndFind(){
        List<String> imgSrcs= Arrays.asList(
                "https://images.unsplash.com/photo-1563783850023-077d97825802?ixlib=rb-1.2" +
                        ".1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=3270&q=80",
                "https://images.unsplash.com/photo-1541004995602-b3e898709909?ixlib=rb-1.2" +
                        ".1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=3270&q=80",
                "https://images.unsplash.com/photo-1592351763700-b9b35a6465ea?ixlib=rb-1.2" +
                        ".1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=3270&q=80"
        );

        Room caravan1=new Room("C-102호",10000,"화장실 : 1, 와이파이 : O, TV : 1, 부엌 : 1, 침대 : 2",
                imgSrcs, CARAVAN,"https://i.imgur.com/M84ivhf.png", 6,2, new MapPosition(15.3,52.5));

        roomRepository.save(caravan1);

        Optional<Room> findByName = roomRepository.findOneByName("C-102호");
        List<Room> rooms = roomRepository.findAll();
        assertThat(findByName.isPresent()).isTrue();
        assertThat(findByName.get()).isEqualTo(caravan1);
        assertThat(rooms.size()).isEqualTo(1);
    }

}