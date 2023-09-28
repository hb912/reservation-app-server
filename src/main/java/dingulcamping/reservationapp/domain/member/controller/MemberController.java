package dingulcamping.reservationapp.domain.member.controller;

import dingulcamping.reservationapp.domain.mail.dto.MailDto;
import dingulcamping.reservationapp.domain.mail.service.MailService;
import dingulcamping.reservationapp.domain.member.dto.*;
import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.domain.member.entity.ResetPwKey;
import dingulcamping.reservationapp.domain.member.exception.NameIsNotCorrectException;
import dingulcamping.reservationapp.domain.member.service.MemberService;
import dingulcamping.reservationapp.domain.member.service.ResetPwKeyService;
import dingulcamping.reservationapp.global.security.AuthUtils;
import dingulcamping.reservationapp.global.security.CookieManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static dingulcamping.reservationapp.global.security.CookieManager.ACCESS_EXP;
import static dingulcamping.reservationapp.global.security.CookieManager.REFRESH_EXP;

@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final MailService mailService;
    private final ResetPwKeyService resetPwKeyService;
    private final AuthUtils authUtils;
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
        CookieManager.addCookie(response, "accessToken", tokens.getAccessToken(), ACCESS_EXP,true);
        CookieManager.addCookie(response, "userRole", tokens.getRole().toLowerCase(), ACCESS_EXP,false);
        if(tokens.getRefreshToken()!=null) {
            CookieManager.addCookie(response, "refreshToken", tokens.getRefreshToken(), REFRESH_EXP,true);
        }
        return ResponseEntity.ok("로그인이 완료되었습니다.");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response){
        CookieManager.addCookie(response, "refreshToken", "", 0,false);
        CookieManager.addCookie(response, "accessToken", "", 0,false);
        CookieManager.addCookie(response, "userRole", "", 0,false);
        response.addHeader("authorization","");
        return ResponseEntity.ok("로그아웃이 완료되었습니다.");
    }

    @GetMapping("/user")
    public ResponseEntity<MemberInfoDto> getUserInfo(){
        Long memberId = authUtils.getMemberId();
        MemberInfoDto result=memberService.getMemberInfo(memberId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/confirmPW")
    public ResponseEntity<Boolean> confirmPassword(@Valid String password, HttpServletRequest request){
        Long memberId = authUtils.getMemberId();
        Boolean isPasswordCorrect=memberService.verifyPassword(memberId, password);
        return ResponseEntity.ok(isPasswordCorrect);
    }

    @PostMapping("/newPassword")
    public ResponseEntity<String> sendVerifyMail(@Valid @RequestBody FindPwReq findPwReq){
        Member findMember = memberService.getMemberByEmail(findPwReq.getEmail());
        if(!findMember.getName().equals(findPwReq.getName())){
            throw new NameIsNotCorrectException();
        }
        String redisKey = UUID.randomUUID().toString();
        ResetPwKey resetPwKey = new ResetPwKey(redisKey, findMember.getId());
        resetPwKeyService.saveRedisKey(resetPwKey);
        MailDto mailDto = new MailDto();
        mailDto.setTo(findMember.getEmail());
        mailDto.setSubject("비밀번호 변경");
        mailService.sendMail(mailDto,redisKey);
        return ResponseEntity.ok("메일 전송 완료");
    }

    @GetMapping("/newPassword")
    public ResponseEntity<MemberIdDto> certificateRedisKey(@Valid String redisKey){
        ResetPwKey findRedisKey = resetPwKeyService.findRedisKey(redisKey);
        return ResponseEntity.ok(new MemberIdDto(findRedisKey.getMemberId()));
    }

    @PatchMapping("/password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePwDto changePwDto){
        memberService.changePassword(changePwDto.getMemberId(),changePwDto.getPassword());
        resetPwKeyService.deleteKey(changePwDto.getRedisKey());
        return ResponseEntity.ok("패스워드 변경 완료");
    }

    @PatchMapping("/user")
    public ResponseEntity<String> memberUpdate(@Valid @RequestBody MemberUpdateDto memberUpdateDto,
                                               HttpServletRequest request){
        Long memberId = authUtils.getMemberId();
        memberService.updateMember(memberId, memberUpdateDto);
        return ResponseEntity.ok("변경 성공");
    }

    @DeleteMapping("/user")
    public ResponseEntity<String> memberDelete(HttpServletRequest request){
        Long memberId = authUtils.getMemberId();
        memberService.deleteMember(memberId);
        return ResponseEntity.ok("탈퇴 성공");
    }
}
