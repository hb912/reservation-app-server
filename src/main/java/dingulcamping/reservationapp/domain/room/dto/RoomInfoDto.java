package dingulcamping.reservationapp.domain.room.dto;

import com.querydsl.core.annotations.QueryProjection;
import dingulcamping.reservationapp.domain.room.entity.MapPosition;
import dingulcamping.reservationapp.domain.room.entity.RoomType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomInfoDto {
    private Long id;
    private String name;
    private int price;
    private RoomType roomType;
    private String icon;
    private int maxPeople;
    private int minPeople;
    private MapPosition position;

    @QueryProjection
    public RoomInfoDto(Long id, String name, int price, RoomType roomType, String icon, int maxPeople, int minPeople, MapPosition position) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.roomType = roomType;
        this.icon = icon;
        this.maxPeople = maxPeople;
        this.minPeople = minPeople;
        this.position = position;
    }
}
