package dingulcamping.reservationapp.domain.member.service;

import dingulcamping.reservationapp.domain.member.dto.LoginDto;
import dingulcamping.reservationapp.domain.member.dto.RegisterReqDto;
import dingulcamping.reservationapp.domain.member.dto.TokenDto;
import dingulcamping.reservationapp.domain.member.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    public void login(){
        //given
        RegisterReqDto registerReqDto = new RegisterReqDto("hb91215@naver.com", "s!4636733", "홍채", "010-5031-8478");
        memberService.register(registerReqDto);
        LoginDto loginDto = new LoginDto("hb91215@naver.com", "s!4636733", false);

        //when
        TokenDto tokens = memberService.login(loginDto);

        //then
        assertThat(tokens.getRole()).isEqualTo(Role.USER);
    }
}