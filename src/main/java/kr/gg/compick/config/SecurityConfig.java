package kr.gg.compick.config;
import kr.gg.compick.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import kr.gg.compick.security.MyUserDetailsService;
import kr.gg.compick.security.jwt.JwtAuthEntryPoint;
import kr.gg.compick.security.jwt.JwtAuthenticationFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    private static final List<String> PERMIT_URLS = List.of(

            "/api/user/regist",
            "/api/user/login/normal",
            "/api/user/check/**",
            "/api/auth/**",
            "/api/test/**",
            "/api/match/**",
            "/lb/who"
    );

    SecurityConfig(JwtTokenProvider jwtTokenProvider,
                MyUserDetailsService myUserDetailsService,
                JwtAuthEntryPoint jwtAuthEntryPoint) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            @Qualifier("corsConfigurationSource") CorsConfigurationSource corsConfigurationSource
    ) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PERMIT_URLS.toArray(new String[0])).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(eh -> eh
                        .authenticationEntryPoint(jwtAuthEntryPoint))
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider, myUserDetailsService),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
