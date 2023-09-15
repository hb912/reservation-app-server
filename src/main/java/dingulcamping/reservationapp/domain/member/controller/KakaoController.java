package dingulcamping.reservationapp.domain.member.controller;

import dingulcamping.reservationapp.domain.member.dto.TokenDto;
import dingulcamping.reservationapp.domain.member.dto.kakao.KakaoTokenDto;
import dingulcamping.reservationapp.domain.member.dto.kakao.KakaoUserInfo;
import dingulcamping.reservationapp.domain.member.service.MemberService;
import dingulcamping.reservationapp.domain.member.service.kakao.KakaoUtils;
import dingulcamping.reservationapp.global.security.CookieManager;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import static dingulcamping.reservationapp.global.security.CookieManager.ACCESS_EXP;
import static dingulcamping.reservationapp.global.security.CookieManager.REFRESH_EXP;

@RequiredArgsConstructor
@Slf4j
@RestController
public class KakaoController {

    @Value("${kakao.key}")
    private String REST_API_KEY;
    private static final String REDIRECT_URI = "http://localhost:8080/api/oauth";
    private final KakaoUtils kakaoUtils;
    private final MemberService memberService;

    @GetMapping("/api/kakao")
    public RedirectView kakaoLoginRequest(RedirectAttributes attributes) {
        String kakaoAuthURL = "https://kauth.kakao.com/oauth/authorize";
        String redirectURL = kakaoAuthURL + "?response_type=code&client_id=" + REST_API_KEY + "&redirect_uri=" + REDIRECT_URI;
        return new RedirectView(redirectURL);
    }

    @GetMapping("/api/oauth")
    public RedirectView kakaoLogin(@RequestParam String code, HttpServletResponse response){
        //인가코드로 토큰 받기
        KakaoTokenDto token = kakaoUtils.getToken(code);
        log.info("accessToken={}",token.getAccess_token());
        log.info("refreshToken={}",token.getRefresh_token());


        //토큰으로 사용자 정보 받아오기
        KakaoUserInfo userInfo = kakaoUtils.getUserInfo(token.getAccess_token());
        log.info("userInfo={}",userInfo);

        TokenDto tokens = memberService.kakaoLogin(userInfo);

        String accessToken="Bearer "+tokens.getAccessToken();
        response.addHeader("authorization", accessToken);
        CookieManager.addCookie(response, "accessToken", tokens.getAccessToken(), ACCESS_EXP,true);
        CookieManager.addCookie(response, "userRole", tokens.getRole().toLowerCase(), ACCESS_EXP,false);
        if(tokens.getRefreshToken()!=null) {
            CookieManager.addCookie(response, "refreshToken", tokens.getRefreshToken(), REFRESH_EXP,true);
        }

        //로그인완료
        return new RedirectView("http://localhost:3000");
    }
}
