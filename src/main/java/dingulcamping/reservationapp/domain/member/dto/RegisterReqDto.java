package dingulcamping.reservationapp.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RegisterReqDto {

    @NotBlank(message="이메일 주소를 입력해주세요")
    @Email(message="이메일 형식으로 입력해주세요.")
    @Length(max=40,message="최대 40자까지 입력 가능합니다.")
    private String email;

    @NotBlank(message="비밀번호를 입력해주세요.")
    @Length(min=8,max=20,message="비밀번호는 최소 8자, 최대 20자로 입력해주세요.")
    private String password;

    @NotBlank(message="이름을 입력해주세요")
    @Length(min=2,max=10,message="이름은 최소 2자, 최대 10자로 입력해주세요")
    private String name;

    @Length(max=13,message="하이픈을 포함하여 13자로 입력해주세요.")
    private String phoneNumber;
}
