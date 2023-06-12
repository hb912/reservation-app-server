package dingulcamping.reservationapp.domain.room.entity;

import dingulcamping.reservationapp.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Room extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;
    private String content;

    @ElementCollection(fetch=FetchType.LAZY)
    private List<String> imgSrc;
    private String roomType;
    private String icon;
    private int maxPeople;
    private int minPeople;

    @Embedded
    private MapPosition mapPosition;
}
