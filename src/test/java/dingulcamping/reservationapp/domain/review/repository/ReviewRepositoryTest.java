package dingulcamping.reservationapp.domain.review.repository;

import dingulcamping.reservationapp.domain.booking.dto.BookingInfoDto;
import dingulcamping.reservationapp.domain.booking.entity.Booking;
import dingulcamping.reservationapp.domain.booking.entity.BookingStatus;
import dingulcamping.reservationapp.domain.booking.repository.BookingRepository;
import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.domain.member.repository.MemberRepository;
import dingulcamping.reservationapp.domain.review.dto.ReviewInfoDto;
import dingulcamping.reservationapp.domain.review.entity.Review;
import dingulcamping.reservationapp.domain.room.entity.MapPosition;
import dingulcamping.reservationapp.domain.room.entity.Room;
import dingulcamping.reservationapp.domain.room.entity.RoomType;
import dingulcamping.reservationapp.domain.room.repository.RoomRepository;
import jakarta.persistence.EntityManager;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewRepositoryTest {

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    EntityManager em;

    @BeforeEach
    public void before(){
        em.createQuery("delete from Room r").executeUpdate();
        em.createQuery("delete from Booking b").executeUpdate();
        em.createQuery("delete from Member m").executeUpdate();
        em.flush();
        em.clear();
        List<String> roomIcons=new ArrayList<>();
        roomIcons.add("hello");
        Room room1=new Room("room1",1000,"hello",roomIcons, RoomType.Caravan,"hello", 6,2,new MapPosition(2.5,3.6));
        Room room2=new Room("room2",1000,"hello",roomIcons, RoomType.Caravan,"hello", 6,2,new MapPosition(2.5,3.6));
        roomRepository.save(room1);
        roomRepository.save(room2);
        Member memberA=new Member("ab@ab.com","memberA","12344456","010-5031-8478");
        Member memberB=new Member("ab@abb.com","memberB","123444577","010-4444-8478");
        memberRepository.save(memberA);
        memberRepository.save(memberB);
        em.flush();
        Date startDate = Date.valueOf("2023-05-18");
        Date endDate=Date.valueOf("2023-05-20");
        Date startDate2 = Date.valueOf("2023-05-20");
        Date endDate2=Date.valueOf("2023-05-24");
        Date startDate3 = Date.valueOf("2023-05-21");
        Date endDate3=Date.valueOf("2023-05-23");
        Booking booking=new Booking(2000,startDate,endDate,3,null,room1,memberA);
        Booking booking2=new Booking(2000,startDate2,endDate2,3,null,room2,memberA);
        Booking booking3=new Booking(2000,startDate3,endDate3,3,null,room1,memberA);
        Booking booking4=new Booking(2000,startDate,endDate,3,null,room2,memberA);
        bookingRepository.save(booking);
        bookingRepository.save(booking2);
        bookingRepository.save(booking3);
        bookingRepository.save(booking4);
        booking.changeBookingStatus(BookingStatus.BOOKING_EXP);
        booking2.changeBookingStatus(BookingStatus.BOOKING_EXP);
        booking3.changeBookingStatus(BookingStatus.BOOKING_EXP);
        booking4.changeBookingStatus(BookingStatus.BOOKING_EXP);
    }

    @Test
    public void createReview(){
        Optional<Member> findMember = memberRepository.findOneByNameAndEmail("memberA", "ab@ab.com");
        PageRequest pageRequest=PageRequest.of(0,3);
        Page<BookingInfoDto> results = bookingRepository.findAllByMemberId(findMember.get().getId(), pageRequest);
        BookingInfoDto bookingInfo = results.getContent().get(0);
        Optional<Booking> result = bookingRepository.findById(bookingInfo.get_id());
        if(!result.isPresent()){
            fail("Booking is not Exist");
        }
        Booking booking=result.get();
        Review review=new Review("hello","my name is",4.5,booking);
        reviewRepository.save(review);
        Optional<Review> findReview = reviewRepository.findById(review.getId());
        assertThat(findReview).isPresent();
        assertThat(findReview.get()).isEqualTo(review);
    }

    @Test
    public void findReviewByRoom(){
        //given
        Optional<Room> room1 = roomRepository.findOneByName("room1");
        Optional<Member> memberA = memberRepository.findOneByNameAndEmail("memberA", "ab@ab.com");
        Date startDate = Date.valueOf("2023-04-18");
        Date endDate=Date.valueOf("2023-04-20");
        Booking booking=new Booking(2000,startDate,endDate,3,null,room1.get(),memberA.get());
        Booking booking2=new Booking(2000,Date.valueOf("2023-04-15"),Date.valueOf("2023-04-16"),
                3,null,room1.get(), memberA.get());
        bookingRepository.save(booking);
        booking.changeBookingStatus(BookingStatus.BOOKING_EXP);
        bookingRepository.save(booking2);
        booking2.changeBookingStatus(BookingStatus.BOOKING_EXP);
        Review review=new Review("hello","my name is",4.5,booking);
        reviewRepository.save(review);

        //when
        PageRequest pageRequest=PageRequest.of(0,3);
        Page<ReviewInfoDto> findReviews = reviewRepository.findByRoom(room1.get(), pageRequest);

        //then
        assertThat(findReviews.getTotalElements()).isEqualTo(1);
        assertThat(findReviews.getContent().get(0)).isEqualTo(review);
    }

    @Test
    public void findReviewByMember(){
        //given
        Optional<Room> room1 = roomRepository.findOneByName("room1");
        Optional<Room> room2 = roomRepository.findOneByName("room2");
        Optional<Member> memberA = memberRepository.findOneByNameAndEmail("memberA", "ab@ab.com");
        Date startDate = Date.valueOf("2023-04-18");
        Date endDate=Date.valueOf("2023-04-20");
        Booking booking=new Booking(2000,startDate,endDate,3,null,room1.get(),memberA.get());
        Booking booking2=new Booking(2000,startDate,endDate,3,null,room2.get(),memberA.get());

        bookingRepository.save(booking);
        booking.changeBookingStatus(BookingStatus.BOOKING_EXP);
        bookingRepository.save(booking2);
        booking2.changeBookingStatus(BookingStatus.BOOKING_EXP);
        Review review=new Review("hello","my name is",4.5,booking);
        Review review2=new Review("hello","my name is",4.5,booking2);
        reviewRepository.save(review);
        reviewRepository.save(review2);

        //when
        List<Review> findReviews = reviewRepository.findByMemberId(memberA.get().getId());

        //then
        assertThat(findReviews.size()).isEqualTo(2);
        assertThat(findReviews).extracting("booking").contains(booking,booking2);
    }
}