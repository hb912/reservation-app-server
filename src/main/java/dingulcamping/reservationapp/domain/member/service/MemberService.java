package dingulcamping.reservationapp.domain.member.service;

import dingulcamping.reservationapp.domain.member.dto.LoginDto;
import dingulcamping.reservationapp.domain.member.dto.RegisterReqDto;
import dingulcamping.reservationapp.domain.member.dto.MemberUpdateDto;
import dingulcamping.reservationapp.domain.member.dto.TokenDto;
import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.domain.member.entity.RefreshToken;
import dingulcamping.reservationapp.domain.member.entity.Role;
import dingulcamping.reservationapp.domain.member.exception.*;
import dingulcamping.reservationapp.domain.member.repository.MemberRepository;
import dingulcamping.reservationapp.domain.member.repository.RefreshTokenRepository;
import dingulcamping.reservationapp.domain.member.repository.ResetPwKeyRepository;
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
    private final RefreshTokenRepository refreshTokenRepository;
    private final ResetPwKeyRepository resetPwKeyRepository;
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

        Member member=new Member(registerReqDto);
        setNewPassword(registerReqDto.getPassword(), member);
        memberRepository.save(member);
        log.info("회원가입완료");
    }


    public TokenDto login(LoginDto loginDto){
        String email=loginDto.getEmail();
        String password=loginDto.getPassword();
        Boolean autoLogin = loginDto.getAutoLogin();

        Member member = getMemberByEmail(email);
        boolean isPasswordCorrect = bCryptPasswordEncoder.matches(password, member.getPassword());

        if(!isPasswordCorrect){
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다. 다시 한번 확인해주세요");
        }

        String accessToken = getAccessToken(member.getId(), member.getRole());
        Role role = member.getRole();
        TokenDto tokenDto = new TokenDto(accessToken, role);

        if(autoLogin){
            String token = getRefreshToken(member.getId(), member.getRole());
            RefreshToken refreshToken = new RefreshToken(token, member.getId());
            refreshTokenRepository.save(refreshToken);
            tokenDto.setRefreshToken(token);
        }

        return tokenDto;
    }

    public String getAccessToken(Long memberId, Role role) {
        log.info("secretKey={}",secretKey);
        return JwtUtils.createJwt(memberId,role,secretKey,accessExp);
    }

    public String getRefreshToken(Long memberId, Role role) {
        log.info("secretKey={}",secretKey);
        return JwtUtils.createJwt(memberId,role,secretKey,refreshExp);
    }

    private void setNewPassword(String password, Member member) {
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        member.setEncryptedPassword(encryptedPassword);
    }

    public Boolean verifyPassword(Long memberId, String password) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        Member member = findMember.get();
        return bCryptPasswordEncoder.matches(password, member.getPassword());
    }

    public Member getMemberByEmail(String email) {
        Optional<Member> findMember = memberRepository.findOneByEmail(email);
        if(findMember.isEmpty()){
            throw new MemberIsNotExistException("해당 이메일은 가입내역이 없습니다. 다시 한번 확인해주세요");
        }
        return findMember.get();
    }

    @Transactional(readOnly = false)
    public void changePassword(Long memberId, String password) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if(findMember.isEmpty()){
            throw new MemberIsNotExistException("멤버 정보가 존재하지 않습니다.");
        }
        setNewPassword(password,findMember.get());
    }

    @Transactional(readOnly = false)
    public void updateMember(Long memberId, MemberUpdateDto memberUpdateDto) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if(findMember.isEmpty()){
            throw new MemberIsNotExistException("유저 정보를 불러오는데 실패했습니다.");
        }
        Member member = findMember.get();
        if(memberUpdateDto.getPassword()!=null){
            setNewPassword(memberUpdateDto.getPassword(),member);
        }
        member.updateMember(memberUpdateDto);
    }
}
