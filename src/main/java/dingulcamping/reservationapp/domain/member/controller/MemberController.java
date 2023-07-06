package dingulcamping.reservationapp.domain.member.controller;

import dingulcamping.reservationapp.domain.member.dto.LoginDto;
import dingulcamping.reservationapp.domain.member.dto.RegisterReqDto;
import dingulcamping.reservationapp.domain.member.dto.TokenDto;
import dingulcamping.reservationapp.domain.member.service.MemberService;
import dingulcamping.reservationapp.global.security.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    @Value("${jwt.secret}")
    private String secretKey;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterReqDto registerReqDto){
        log.info("회원가입 시작");
        memberService.register(registerReqDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response){
        TokenDto tokens = memberService.login(loginDto);
        log.info("accessToken={}",tokens.getAccessToken());
        String accessToken="Bearer "+tokens.getAccessToken();
        response.addHeader("authorization", accessToken);
        if(!tokens.getRefreshToken().isEmpty()) {
            Cookie cookie = new Cookie("refreshToken", tokens.getRefreshToken());
            response.addCookie(cookie);
        }
        return ResponseEntity.ok("로그인이 완료되었습니다.");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response){
        Cookie cookie = new Cookie("refreshToken", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.addHeader("authorization","");
        return ResponseEntity.ok("로그아웃이 완료되었습니다.");
    }

    @GetMapping("/confirmPW")
    public ResponseEntity<Boolean> confirmPassword(@Valid String password, HttpServletRequest request){
        final String authorization=request.getHeader(HttpHeaders.AUTHORIZATION);
        String token=authorization.split(" ")[1];
        Long memberId= JwtUtils.getMemberId(token,secretKey);
        Boolean isPasswordCorrect=memberService.verifyPassword(memberId, password);
        return ResponseEntity.ok(isPasswordCorrect);
    }
}
