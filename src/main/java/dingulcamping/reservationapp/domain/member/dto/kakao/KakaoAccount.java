package dingulcamping.reservationapp.domain.member.dto.kakao;

import lombok.Data;

@Data
public class KakaoAccount {
    private boolean profile_nickname_needs_agreement;
    private KakaoProfile profile;
    private boolean has_email;
    private boolean email_needs_agreement;
    public String email;
}
