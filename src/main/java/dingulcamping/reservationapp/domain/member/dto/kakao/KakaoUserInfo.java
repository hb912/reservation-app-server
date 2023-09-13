package dingulcamping.reservationapp.domain.member.dto.kakao;

import lombok.Data;

@Data
public class KakaoUserInfo {
    private Long id;
    private String connected_at;
    private KakaoAccount kakao_account;
    private KakaoProfile properties;
}
