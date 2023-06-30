package dingulcamping.reservationapp.domain.member.dto;

import dingulcamping.reservationapp.domain.member.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenDto {

    private String accessToken;
    private String refreshToken;
    private Role role;

    public TokenDto(String accessToken, Role role) {
        this.accessToken = accessToken;
        this.role = role;
    }

    public TokenDto(String accessToken, String refreshToken, Role role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.role = role;
    }
}
