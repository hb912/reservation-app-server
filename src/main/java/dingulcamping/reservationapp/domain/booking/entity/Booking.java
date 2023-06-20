package dingulcamping.reservationapp.domain.booking.entity;

import com.querydsl.core.annotations.QueryEntity;
import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.domain.review.entity.Review;
import dingulcamping.reservationapp.domain.room.entity.Room;
import dingulcamping.reservationapp.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
@QueryEntity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Booking extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private int price;
    private Date startDate;
    private Date endDate;

    @ElementCollection(fetch=FetchType.LAZY)
    private List<Date> processDate=new ArrayList<>();
    private int peopleNumber;
    private String requirements;

    @Enumerated(value=EnumType.STRING)
    private BookingStatus status;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="room_id")
    private Room room;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToOne(mappedBy="booking", fetch= FetchType.LAZY)
    private Review review;

    public Booking(int price, Date startDate, Date endDate, int peopleNumber,String requirements, Room room,
                   Member member
                   ) {
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        getProcessDate(startDate, endDate);
        this.peopleNumber = peopleNumber;
        this.requirements=requirements;
        this.room = room;
        this.member = member;
        this.status=BookingStatus.BOOKING_REQ;
    }

    public void createReview(Review review) {
        this.review = review;
    }

    public void deleteReview(){
        this.review=null;
    }

    public void changeBookingStatus(BookingStatus bookingStatus){
        this.status=bookingStatus;
    }

    private void getProcessDate(Date startDate, Date endDate) {
        Date curDate= startDate;
        while(curDate.before(endDate)){
            processDate.add(curDate);
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(curDate);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            curDate=new Date(calendar.getTimeInMillis());
        }
    }
}
