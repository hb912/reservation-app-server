package dingulcamping.reservationapp.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class ChangePwDto {

    private Long memberId;
    private String redisKey;

    @NotBlank(message="비밀번호를 입력해주세요.")
    @Length(min=2,max=20,message="비밀번호는 최소 8자, 최대 20자로 입력해주세요.")
    private String password;
}
