package kr.gg.compick.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import kr.gg.compick.filter.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // 인증 없이 접근 가능한 URL 목록
    // 로그인, 회원가입 등, (로그인 필요 없는 작업 처리 추가)
    private static final List<String> PERMIT_ALL_URLS = List.of(
        "/api/member/login",
        "/api/member/regist"
    );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CSRF 비활성화 (API 서버이므로)
            .csrf(csrf -> csrf.disable())

            // 요청 경로별 접근 권한 설정
            .authorizeHttpRequests(auth -> auth
                // 인증 없이 접근 가능한 URL 목록
                .requestMatchers(PERMIT_ALL_URLS.toArray(String[]::new)).permitAll()
                // 나머지는 인증 필요
                .anyRequest().authenticated()
            )
            //auth 존재 하지 않을 경우 filter패키지의 JwtAuthenticationFilter를 사용하여 JWT 인증 처리 ★
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            // HTTP Basic 사용 (필요시 삭제 가능)
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}