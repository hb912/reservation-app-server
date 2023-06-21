package dingulcamping.reservationapp.domain.member.controller;

import dingulcamping.reservationapp.domain.member.dto.RegisterReqDto;
import dingulcamping.reservationapp.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterReqDto registerReqDto){
        log.info("회원가입 시작");
        memberService.register(registerReqDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다");
    }
}
