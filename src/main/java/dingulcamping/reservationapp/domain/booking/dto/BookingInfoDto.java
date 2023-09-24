package dingulcamping.reservationapp.domain.booking.dto;

import com.querydsl.core.annotations.QueryProjection;
import dingulcamping.reservationapp.domain.booking.entity.BookingStatus;
import dingulcamping.reservationapp.domain.review.entity.Review;
import dingulcamping.reservationapp.domain.room.dto.SimpleRoomDto;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class BookingInfoDto {
    private Long _id;
    private int price;
    private Date startDate;
    private Date endDate;
    private int peopleNumber;
    private String requirements;
    private String status;
    private SimpleRoomDto roomID;
    private String name;
    private Boolean isReviewed;

    @QueryProjection
    public BookingInfoDto(Long _id, int price, Date startDate, Date endDate, int peopleNumber
            , String requirements, BookingStatus status, Long roomId, String roomName, String memberName,
                          Review review) {
        this._id = _id;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.peopleNumber = peopleNumber;
        this.requirements = requirements;
        this.status = status.getStatus();
        this.roomID = new SimpleRoomDto(roomId, roomName);
        this.name = memberName;
        if(review==null){
            this.isReviewed=false;
        }else{
            this.isReviewed=true;
        }
    }
}
