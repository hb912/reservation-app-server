package dingulcamping.reservationapp.domain.booking.dto;

import dingulcamping.reservationapp.domain.booking.entity.BookingStatus;
import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.domain.review.entity.Review;
import dingulcamping.reservationapp.domain.room.entity.Room;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class BookingCreateDto {

    @NotNull
    private int price;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
    private int peopleNumber;

    @Length(max=45,message="요구사항은 최대 45자로 입력해주세요.")
    private String requirements;

    @NotNull
    private Long roomID;
}
