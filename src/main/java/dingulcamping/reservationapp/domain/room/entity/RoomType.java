package dingulcamping.reservationapp.domain.room.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RoomType {
    CARAVAN, TENT, GLAMPING;

    @JsonCreator
    public static RoomType from(String s) {
        return RoomType.valueOf(s.toUpperCase());
    }
}
