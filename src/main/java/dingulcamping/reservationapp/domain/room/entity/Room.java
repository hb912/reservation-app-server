package dingulcamping.reservationapp.domain.room.entity;

import dingulcamping.reservationapp.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Room extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable = false)
    private String name;
    private int price;
    private String content;

    @ElementCollection(fetch=FetchType.LAZY)
    private List<String> imgSrc=new ArrayList<>();

    @Enumerated(value=EnumType.STRING)
    private RoomType roomType;

    private String icon;
    private int maxPeople;
    private int minPeople;

    @Embedded
    private MapPosition mapPosition;

    public Room(String name, int price, String content, List<String> imgSrc, RoomType roomType, String icon,
                int maxPeople, int minPeople, MapPosition mapPosition) {
        this.name = name;
        this.price = price;
        this.content = content;
        this.imgSrc.addAll(imgSrc);
        this.roomType = roomType;
        this.icon = icon;
        this.maxPeople = maxPeople;
        this.minPeople = minPeople;
        this.mapPosition = mapPosition;
    }

    public void addImg(String imgSrc){
        this.imgSrc.add(imgSrc);
    }

    public void deleteImg(String imgSrc){
        this.imgSrc.remove(imgSrc);
    }
}
