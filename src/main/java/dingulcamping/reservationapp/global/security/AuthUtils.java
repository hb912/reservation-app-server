package dingulcamping.reservationapp.global.security;

import dingulcamping.reservationapp.domain.member.entity.Member;
import dingulcamping.reservationapp.domain.member.exception.MemberIsNotExistException;
import dingulcamping.reservationapp.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AuthUtils {

    private final MemberRepository memberRepository;
    @Value("${jwt.secret}")
    private String secretKey;

    @Transactional(readOnly = true)
    public Member getMember(HttpServletRequest request) {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorization.split(" ")[1];
        try {
            Long memberId = JwtUtils.getMemberId(token, secretKey);
            return memberRepository.findById(memberId).orElseThrow(MemberIsNotExistException::new);
        } catch (Exception e) {
            return null;
        }
    }

    public Long getMemberId(HttpServletRequest request) {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorization.split(" ")[1];
        try {
            return JwtUtils.getMemberId(token, secretKey);
        } catch (Exception e) {
            return null;
        }
    }
}
