package dingulcamping.reservationapp.domain.member.service.kakao;

import dingulcamping.reservationapp.domain.member.dto.kakao.KakaoTokenDto;
import dingulcamping.reservationapp.domain.member.dto.kakao.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
@RequiredArgsConstructor
public class KakaoUtils {

    private final WebClient webClient;
    private static final String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    private static final String REDIRECT_URI = "http://localhost:8080/api/oauth";
    private static final String GRANT_TYPE = "authorization_code";
    @Value("${kakao.key}")
    private String CLIENT_ID;

    public KakaoTokenDto getToken(String code){
        String uri = TOKEN_URI + "?grant_type=" + GRANT_TYPE + "&client_id=" + CLIENT_ID+ "&redirect_uri=" + REDIRECT_URI + "&code=" + code;
        System.out.println(uri);

        return webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(KakaoTokenDto.class)
                .blockFirst();
    }

    public KakaoUserInfo getUserInfo(String token){
        String uri="https://kapi.kakao.com/v2/user/me";
        System.out.println("uri = " + uri);
        System.out.println("token = " + token);
        String bearerToken="Bearer "+token.toString();
        return webClient.get()
                .uri(uri)
                .header("Authorization", bearerToken)
                .retrieve()
                .bodyToFlux(KakaoUserInfo.class)
                .blockFirst();
    }
}
