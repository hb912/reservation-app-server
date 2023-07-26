package dingulcamping.reservationapp.domain.room.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleRoomDto {
    private Long _id;
    private String name;

    @QueryProjection
    public SimpleRoomDto(Long _id, String name) {
        this._id = _id;
        this.name = name;
    }
}
