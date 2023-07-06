package dingulcamping.reservationapp.domain.booking.repository;

import dingulcamping.reservationapp.domain.booking.entity.Booking;
import dingulcamping.reservationapp.domain.booking.entity.BookingStatus;
import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.domain.member.repository.MemberRepository;
import dingulcamping.reservationapp.domain.room.entity.MapPosition;
import dingulcamping.reservationapp.domain.room.entity.Room;
import dingulcamping.reservationapp.domain.room.entity.RoomType;
import dingulcamping.reservationapp.domain.room.repository.RoomRepository;
import jakarta.persistence.EntityManager;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookingRepositoryTest {

    @Autowired BookingRepository bookingRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired RoomRepository roomRepository;
    @Autowired
    EntityManager em;

    @BeforeEach
    public void before(){
        List<String> roomIcons=new ArrayList<>();
        roomIcons.add("hello");
        Room room1=new Room("room1",1000,"hello",roomIcons, RoomType.CARAVAN,"hello", 6,2,new MapPosition(2.5,3.6));
        Room room2=new Room("room2",1000,"hello",roomIcons, RoomType.CARAVAN,"hello", 6,2,new MapPosition(2.5,3.6));
        roomRepository.save(room1);
        roomRepository.save(room2);
        Member memberA=new Member("ab@ab.com","memberA","12344456","010-5031-8478");
        Member memberB=new Member("ab@abb.com","memberB","123444577","010-4444-8478");
        memberRepository.save(memberA);
        memberRepository.save(memberB);
        em.flush();
        Date startDate = getDate(2023,7,18);
        Date endDate=getDate(2023,7,20);
        Date startDate2 = getDate(2023,7,22);
        Date endDate2=getDate(2023,7,24);
        Date startDate3 = getDate(2023,7,21);
        Date endDate3=getDate(2023,7,23);
        Booking booking=new Booking(2000,startDate,endDate,3,null,room1,memberA);
        Booking booking2=new Booking(2000,startDate2,endDate2,3,null,room2,memberA);
        Booking booking3=new Booking(2000,startDate3,endDate3,3,null,room1,memberA);
        Booking booking4=new Booking(2000,startDate,endDate,3,null,room2,memberA);
        bookingRepository.save(booking);
        bookingRepository.save(booking2);
        bookingRepository.save(booking3);
        bookingRepository.save(booking4);
        booking.changeBookingStatus(BookingStatus.BOOKING_CONFIRM);
        booking2.changeBookingStatus(BookingStatus.BOOKING_CONFIRM);
        booking3.changeBookingStatus(BookingStatus.BOOKING_CONFIRM);
    }

    @Test
    public void findAllByMemberId(){
        Optional<Member> findMember = memberRepository.findOneByNameAndEmail("memberA", "ab@ab.com");
        PageRequest pageRequest=PageRequest.of(0,3);
        Page<Booking> results = bookingRepository.findAllByMemberId(findMember.get().getId(), pageRequest);
        assertThat(results.getContent().size()).isEqualTo(3);
    }

    @Test
    public void findDisableDateByRoom(){
        Optional<Room> findRoom = roomRepository.findOneByName("room1");
        List<Date> results = bookingRepository.findDisableDatesByRoomId(findRoom.get().getId());
        System.out.println("results = " + results);
    }

    @Test
    public void findDisableRoomByDate(){
        List<java.sql.Date> dates = Arrays.asList(
                getDate(2023,07,21),
                getDate(2023,07,22)
        );
        List<Room> results = bookingRepository.findDisableRoomsByDate(dates);
        assertThat(results.size()).isEqualTo(2);
    }

    @Test
    public void findReq(){
        List<Booking> results = bookingRepository.findRequests();
        assertThat(results.size()).isEqualTo(1);
    }

    @Test
    public void findConfirm(){
        List<Booking> results = bookingRepository.findConfirms();
        assertThat(results.size()).isEqualTo(3);
    }

    public Date getDate(int year,int month,int date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, date);
        return new Date(calendar.getTimeInMillis());
    }
}