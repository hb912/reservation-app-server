package dingulcamping.reservationapp.domain.review.dto;

import com.querydsl.core.annotations.QueryProjection;
import dingulcamping.reservationapp.domain.member.dto.SimpleMemberInfo;
import dingulcamping.reservationapp.domain.room.dto.SimpleRoomDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewInfoDto {
    private Long _id;
    private SimpleRoomDto roomID;
    private SimpleMemberInfo userID;
    private Long bookingID;
    private String title;
    private String content;
    private Double grade;

    @QueryProjection
    public ReviewInfoDto(Long _id, Long roomId, String roomName, Long memberId, String memberName, Long bookingId,
                         String title, String content, Double grade) {
        this._id = _id;
        this.roomID=new SimpleRoomDto(roomId,roomName);
        this.userID=new SimpleMemberInfo(memberId,memberName);
        this.bookingID=bookingId;
        this.title=title;
        this.content=content;
        this.grade=grade;
    }
}
