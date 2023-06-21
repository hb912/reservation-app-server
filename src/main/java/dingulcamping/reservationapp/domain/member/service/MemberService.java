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
        String encryptedPassword = bCryptPasswordEncoder.encode(registerReqDto.getPassword());
        Member member=new Member(registerReqDto);
        member.setEncryptedPassword(encryptedPassword);
        memberRepository.save(member);
        log.info("회원가입완료");
    }
}
