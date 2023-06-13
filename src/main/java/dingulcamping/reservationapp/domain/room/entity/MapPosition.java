package dingulcamping.reservationapp.domain.room.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MapPosition {
    @Column(name="top")
    private double top;
    @Column(name="\"right\"")
    private double right;
}
