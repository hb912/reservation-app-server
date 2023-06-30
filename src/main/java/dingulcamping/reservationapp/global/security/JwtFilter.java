package dingulcamping.reservationapp.global.security;

import dingulcamping.reservationapp.domain.member.entity.Role;
import dingulcamping.reservationapp.domain.member.service.RefreshTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final String secretKey;
    private final RefreshTokenService refreshTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization=request.getHeader(HttpHeaders.AUTHORIZATION);

        log.info("authorization : {}", authorization);

        if(authorization==null || !authorization.startsWith("Bearer ")){
            //인증이 안되었을때 block
            log.error("authorization is null");
            filterChain.doFilter(request,response);
            return;
        }

        Long memberId=null;
        Role role=null;

        //Token 꺼내기
        String token=authorization.split(" ")[1];
        log.info("token={}",token);
        log.info("secretKey={}",secretKey);
//        //Token Expired 되었는지 여부
        if(JwtUtils.isExpired(token,secretKey)){
            log.error("Access Token이 만료되었습니다");
        }else{
            memberId = JwtUtils.getMemberId(token, secretKey);
            role=Role.valueOf(JwtUtils.getRole(token,secretKey));
        }

        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(memberId,
                null, List.of(new SimpleGrantedAuthority(role.toString())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);

    }