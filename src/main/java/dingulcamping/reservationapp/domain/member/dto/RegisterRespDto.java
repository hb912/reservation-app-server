package dingulcamping.reservationapp.domain.member.dto;

import dingulcamping.reservationapp.domain.member.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RegisterRespDto {

    private Long id;
    private String email;
    private String name;
    private String password;
    private String phoneNumber;
    private Role role;
    private String refreshToken;



}
