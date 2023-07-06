package dingulcamping.reservationapp.domain.booking.entity;

import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.domain.member.repository.MemberRepository;
import dingulcamping.reservationapp.domain.room.entity.MapPosition;
import dingulcamping.reservationapp.domain.room.entity.Room;
import dingulcamping.reservationapp.domain.room.entity.RoomType;
import dingulcamping.reservationapp.domain.room.repository.RoomRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookingEntityTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    EntityManager em;

    @Test
    public void setProcessDate(){
        List<String> roomIcons=new ArrayList<>();
        roomIcons.add("hello");
        Room room=new Room("room1",1000,"hello",roomIcons, RoomType.CARAVAN,"hello", 6,2,new MapPosition(2.5,3.6));
        roomRepository.save(room);
        Optional<Member> findMember = memberRepository.findOneByNameAndEmail("memberA", "ab@ab.com");
        em.flush();
        Date startDate = Date.valueOf("2023-04-18");
        Date endDate = Date.valueOf("2023-04-20");
        Booking booking=new Booking(2000,startDate,endDate,3,null,room,findMember.get());
        Assertions.assertThat(booking.getProcessDate())
                .containsExactly(Date.valueOf("2023-04-18"),Date.valueOf("2023-04-19"));
    }

    public Date getDate(int year,int month,int date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, date);
        return new Date(calendar.getTimeInMillis());
    }
}