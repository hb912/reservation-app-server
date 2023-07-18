package dingulcamping.reservationapp.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FindPwReq {

    @NotBlank(message="이메일 주소를 입력해주세요")
    @Email(message="이메일 형식으로 입력해주세요.")
    @Length(max=40,message="최대 40자까지 입력 가능합니다.")
    private String email;

    @NotBlank(message="이름을 입력해주세요")
    @Length(min=2,max=10,message="이름은 최소 2자, 최대 10자로 입력해주세요")
    private String name;
}
