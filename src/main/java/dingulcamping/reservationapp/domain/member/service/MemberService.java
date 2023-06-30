package dingulcamping.reservationapp.domain.member.service;

import dingulcamping.reservationapp.domain.member.dto.LoginDto;
import dingulcamping.reservationapp.domain.member.dto.RegisterReqDto;
import dingulcamping.reservationapp.domain.member.dto.TokenDto;
import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.domain.member.entity.RefreshToken;
import dingulcamping.reservationapp.domain.member.entity.Role;
import dingulcamping.reservationapp.domain.member.exception.EmailAlreadyExistException;
import dingulcamping.reservationapp.domain.member.exception.MemberIsNotExistException;
import dingulcamping.reservationapp.domain.member.exception.PasswordNotMatchException;
import dingulcamping.reservationapp.domain.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import dingulcamping.reservationapp.global.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${jwt.secret}")
    private String secretKey;
    private Long accessExp=30*60*1000l;
    private Long refreshExp=24*60*60*1000l;

    @Transactional(readOnly = false)
    public void register(RegisterReqDto registerReqDto){
        if(memberRepository.findOneByEmail(registerReqDto.getEmail()).isPresent()){
            throw new EmailAlreadyExistException("이 이메일은 현재 사용중입니다. 다른 이메일을 입력해 주세요.");
        }

        String encryptedPassword = bCryptPasswordEncoder.encode(registerReqDto.getPassword());
        Member member=new Member(registerReqDto);
        member.setEncryptedPassword(encryptedPassword);
        memberRepository.save(member);
        log.info("회원가입완료");
    }

    public TokenDto login(LoginDto loginDto){
        String email=loginDto.getEmail();
        String password=loginDto.getPassword();
        Boolean autoLogin = loginDto.getAutoLogin();

        Optional<Member> findMember = memberRepository.findOneByEmail(email);
        if(findMember.isEmpty()){
            throw new MemberIsNotExistException("해당 이메일은 가입내역이 없습니다. 다시 한번 확인해주세요");
        }

        Member member = findMember.get();
        boolean isPasswordCorrect = bCryptPasswordEncoder.matches(password, member.getPassword());

        if(!isPasswordCorrect){
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다. 다시 한번 확인해주세요");
        }

        String accessToken = getAccessToken(member.getId(), member.getRole());
        Role role = member.getRole();
        TokenDto tokenDto = new TokenDto(accessToken, role);

        if(autoLogin){
        }

        return tokenDto;
    }

    public String getAccessToken(Long memberId, Role role) {
        log.info("secretKey={}",secretKey);
        return JwtUtils.createJwt(memberId,role,secretKey,accessExp);
    }

}
