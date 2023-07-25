package dingulcamping.reservationapp.domain.booking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRoomDto {
    private Long _id;
    private String name;

    public BookingRoomDto(Long _id, String name) {
        this._id = _id;
        this.name = name;
    }
}
