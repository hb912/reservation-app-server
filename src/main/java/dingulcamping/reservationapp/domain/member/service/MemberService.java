package dingulcamping.reservationapp.domain.member.service;

import dingulcamping.reservationapp.domain.member.dto.*;
import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.domain.member.entity.RefreshToken;
import dingulcamping.reservationapp.domain.member.entity.Role;
import dingulcamping.reservationapp.domain.member.exception.EmailAlreadyExistException;
import dingulcamping.reservationapp.domain.member.exception.MemberIsNotExistException;
import dingulcamping.reservationapp.domain.member.exception.PasswordNotMatchException;
import dingulcamping.reservationapp.domain.member.repository.MemberRepository;
import dingulcamping.reservationapp.domain.member.repository.RefreshTokenRepository;
import dingulcamping.reservationapp.global.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${jwt.secret}")
    private String secretKey;
    private Long accessExp=30*60*1000l;
    private Long refreshExp=31*24*60*60*1000l;

    @Transactional(readOnly = false)
    public void register(RegisterReqDto registerReqDto){
        if(memberRepository.findOneByEmail(registerReqDto.getEmail()).isPresent()){
            throw new EmailAlreadyExistException();
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
            throw new PasswordNotMatchException();
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
        return JwtUtils.createJwt(memberId,role,secretKey,accessExp);
    }

    public String getRefreshToken(Long memberId, Role role) {
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
            throw new MemberIsNotExistException();
        }
        return findMember.get();
    }

    @Transactional(readOnly = false)
    public void changePassword(Long memberId, String password) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if(findMember.isEmpty()){
            throw new MemberIsNotExistException();
        }
        setNewPassword(password,findMember.get());
    }

    @Transactional(readOnly = false)
    public void updateMember(Long memberId, MemberUpdateDto memberUpdateDto) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if(findMember.isEmpty()){
            throw new MemberIsNotExistException();
        }
        Member member = findMember.get();
        if(memberUpdateDto.getPassword()!=null){
            setNewPassword(memberUpdateDto.getPassword(),member);
        }
        member.updateMember(memberUpdateDto);
    }

    @Transactional(readOnly = false)
    public void deleteMember(Long memberId){
        memberRepository.deleteById(memberId);
    }

    public Page<MemberInfoDto> getAllByName(String name,Pageable pageable){
        return memberRepository.findAllByName(name,pageable);
    }
}
