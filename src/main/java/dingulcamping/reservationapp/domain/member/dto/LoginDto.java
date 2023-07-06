package dingulcamping.reservationapp.domain.member.dto;

import dingulcamping.reservationapp.domain.booking.repository.BookingRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginDto {

    @NotBlank(message="이메일 주소를 입력해주세요")
    @Email(message="이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message="비밀번호를 입력해주세요.")
    private String password;

    @NotNull
    private Boolean autoLogin;
}
