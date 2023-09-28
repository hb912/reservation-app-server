package dingulcamping.reservationapp.global.config;

import dingulcamping.reservationapp.domain.member.repository.RefreshTokenRepository;
import dingulcamping.reservationapp.domain.member.service.RefreshTokenService;
import dingulcamping.reservationapp.global.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@Import(WebSecurityConfig.class)
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig{

    @Value("${jwt.secret}")
    private String secretKey;
    private final RefreshTokenService refreshTokenService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((authz)-> authz
                        .requestMatchers(HttpMethod.GET,"/api/confirmPW").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/user").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/logout").authenticated()
                        .requestMatchers(HttpMethod.PATCH,"/api/user").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/api/user").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/booking/create").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/booking/confirm").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/booking/user").authenticated()
                        .requestMatchers(HttpMethod.PATCH,"/api/booking/cancel").authenticated()
                        .requestMatchers(HttpMethod.PATCH,"/api/review/").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/api/review/").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/review/create").authenticated()
                        .requestMatchers(HttpMethod.POST,"/api/room/**").hasRole("ADMIN")
                        .requestMatchers("/api/admin/**").hasAnyAuthority("ADMIN","ROLE_ADMIN")
                        .requestMatchers("/**").permitAll())
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(config->config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //session 사용 안함
                .addFilterBefore(new JwtFilter(secretKey,refreshTokenService),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
