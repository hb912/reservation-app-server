package dingulcamping.reservationapp.domain.member.entity;

import dingulcamping.reservationapp.domain.room.entity.RoomType;

public enum Role {
    ADMIN, USER;

    public String toLowerCase() {
        return name().toLowerCase();
    }
}
