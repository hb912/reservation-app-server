package dingulcamping.reservationapp.domain.room.dto;

import dingulcamping.reservationapp.domain.room.entity.MapPosition;
import dingulcamping.reservationapp.domain.room.entity.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomCreateDto {

    @NotNull
    private String name;
    @NotNull
    private int price;
    @NotNull
    private String content;
    @NotNull
    private List<String> imgSrc=new ArrayList<>();
    @NotNull
    private RoomType roomType;
    @NotNull
    private String icon;
    @NotNull
    private int maxPeople;
    @NotNull
    private int minPeople;
    @NotNull
    private MapPosition position;


}
