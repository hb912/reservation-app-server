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

    @GetMapping("/api/kakao")
    public RedirectView kakaoLoginRequest(RedirectAttributes attributes) {
        String kakaoAuthURL = "https://kauth.kakao.com/oauth/authorize";
        String redirectURL = kakaoAuthURL + "?response_type=code&client_id=" + REST_API_KEY + "&redirect_uri=" + REDIRECT_URI;
        return new RedirectView(redirectURL);
    }

}
