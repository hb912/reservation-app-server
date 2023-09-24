package dingulcamping.reservationapp.domain.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateDto {

    @NotNull
    private Long roomID;

    @NotNull
    private Long bookingID;

    @Length(max=10000,message="내용은 10000자 이내로 입력해주십시오.")
    private String content;

    @NotBlank(message="제목을 입력해 주십시오.")
    @Length(max=100,message="제목은 100자 이내로 입력해 주십시오.")
    private String title;

    private Double grade;
    private String name;
}
