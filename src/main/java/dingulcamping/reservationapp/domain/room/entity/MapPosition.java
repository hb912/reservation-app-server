package dingulcamping.reservationapp.domain.room.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
public class MapPosition {
    private int top;
    private int right;
}
