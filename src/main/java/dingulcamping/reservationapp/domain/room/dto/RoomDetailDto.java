package dingulcamping.reservationapp.domain.room.dto;

import com.querydsl.core.annotations.QueryProjection;
import dingulcamping.reservationapp.domain.room.entity.MapPosition;
import dingulcamping.reservationapp.domain.room.entity.Room;
import dingulcamping.reservationapp.domain.room.entity.RoomType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RoomDetailDto {
    private Long id;
    private String name;
    private int price;
    private String content;
    private List<String> imgSrc=new ArrayList<>();
    private RoomType roomType;
    private String icon;
    private int maxPeople;
    private int minPeople;
    private MapPosition position;

    public RoomDetailDto(Room room){
        this.id=room.getId();
        this.name=room.getName();
        this.price=room.getPrice();
        this.content=room.getContent();
        this.imgSrc=room.getImgSrc();
        this.roomType = room.getRoomType();
        this.icon = room.getIcon();
        this.maxPeople = room.getMaxPeople();
        this.minPeople = room.getMinPeople();
        this.position = room.getMapPosition();
    }
}
