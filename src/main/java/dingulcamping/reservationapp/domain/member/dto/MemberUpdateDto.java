package dingulcamping.reservationapp.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
public class MemberUpdateDto {

    @Length(min=8,max=20,message="비밀번호는 최소 8자, 최대 20자로 입력해주세요.")
    private String password;

    @Length(min=2,max=10,message="이름은 최소 2자, 최대 10자로 입력해주세요")
    private String name;

    @Length(max=13,message="하이픈을 포함하여 13자로 입력해주세요.")
    private String phoneNumber;
}
