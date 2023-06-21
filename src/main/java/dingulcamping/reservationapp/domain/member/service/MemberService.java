package dingulcamping.reservationapp.domain.member.service;

import dingulcamping.reservationapp.domain.member.dto.RegisterReqDto;
import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.domain.member.exception.EmailAlreadyExistException;
import dingulcamping.reservationapp.domain.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
}
