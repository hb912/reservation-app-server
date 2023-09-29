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
import java.util.Arrays;

import static dingulcamping.reservationapp.global.security.CookieManager.ACCESS_EXP;
import static dingulcamping.reservationapp.global.security.CookieManager.getCookie;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final String secretKey;
    private final RefreshTokenService refreshTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = getCookie(request, "accessToken");
        log.info("accessToken : {}", accessToken);

        if(accessToken==null){
            //인증이 안되었을때 block
            log.error("authorization is null");
            filterChain.doFilter(request,response);
            return;
        }

        Long memberId=null;
        Role role=null;

        //Token Expired 되었는지 여부
        if(JwtUtils.isExpired(accessToken,secretKey)){
            log.error("Access Token이 만료되었습니다");
            String refreshToken = getCookie(request,"refreshToken");
            if(refreshToken==null){
                filterChain.doFilter(request,response);
                return;
            }
            if(JwtUtils.isExpired(refreshToken,secretKey)||!checkRefreshToken(refreshToken,secretKey)){
                log.error("RefreshToken이 만료되었습니다.");
                filterChain.doFilter(request,response);
                return;
            }
            memberId=JwtUtils.getMemberId(refreshToken,secretKey);
            role=Role.valueOf(JwtUtils.getRole(refreshToken,secretKey));
            String newAccessToken = JwtUtils.createJwt(memberId, role, secretKey, 30 * 60 * 1000l);
            response.addHeader("authorization", "Bearer "+newAccessToken);
            CookieManager.addCookie(response, "accessToken", newAccessToken, ACCESS_EXP,true);
            CookieManager.addCookie(response, "userRole", role.toLowerCase(), ACCESS_EXP,false);
        }else{
            memberId = JwtUtils.getMemberId(accessToken, secretKey);
            role=Role.valueOf(JwtUtils.getRole(accessToken,secretKey));
        }

        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(memberId,
                null, Arrays.asList(new SimpleGrantedAuthority("ROLE_"+role.toString())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }

    public Boolean checkRefreshToken(String token, String secretKey){
        try{
            Long memberId = JwtUtils.getMemberId(token, secretKey);
            Long findMemberId = refreshTokenService.getMemberId(token);
            return findMemberId==memberId;
        }catch(ExpiredJwtException e){
            return false;
        }
    }

    private String getRefreshToken(HttpServletRequest request) {
        try{
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("refreshToken")){
                    String refreshToken=cookie.getValue();
                    return refreshToken;
                }
            }
            log.error("RefreshToken 없음");
            return null;
        }catch(NullPointerException e){
            log.error("RefreshToken 없음.");
            return null;
        }
    }
}
