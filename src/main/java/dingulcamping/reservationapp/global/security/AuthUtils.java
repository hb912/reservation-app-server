package dingulcamping.reservationapp.global.security;

import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.domain.member.exception.MemberIsNotExistException;
import dingulcamping.reservationapp.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthUtils {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member getMember() {
        Long memberId = getMemberId();
        return memberRepository.findById(memberId).orElseThrow(MemberIsNotExistException::new);
    }

    public Long getMemberId() {
        String memberId = SecurityContextHolder.getContext().getAuthentication().getName();
        return Long.valueOf(memberId);
    }
}
