package dingulcamping.reservationapp.domain.room.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RoomType {
    Caravan, Tent, Glamping;

    @JsonCreator
    public static RoomType from(String s) {
        return RoomType.valueOf(s);
    }
}
