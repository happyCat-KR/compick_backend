package kr.gg.compick.security.jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.gg.compick.security.MyUserDetailsService;
import kr.gg.compick.security.UserDetailsImpl;
import kr.gg.compick.security.jwt.JwtTokenProvider.TokenCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final MyUserDetailsService userDetailsService;
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class); 

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, MyUserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
            FilterChain fc) throws ServletException, IOException {
        String bearer = req.getHeader("Authorization");
        String token = null;
         String uri = req.getRequestURI();

        // ✅ 카카오 인증/콜백 요청은 JWT 검사 생략
        if (uri.startsWith("/api/auth/")) {
            fc.doFilter(req, res);
            return;
        }

System.out.println("================================");
    Enumeration<String> headerNames = req.getHeaderNames();

    while (headerNames.hasMoreElements()) {
        String headerName1 = headerNames.nextElement();
        String headerValue = req.getHeader(headerName1);
        System.out.println("[헤더] " + headerName1 + " = " + headerValue);
    }

System.out.println("================================");














        if (bearer != null && bearer.startsWith("Bearer ")) {
            token = bearer.substring(7);
        }System.out.println("[보드]"+token);

        TokenCheck tc = tokenProvider.check(token);
        switch (tc.status()) {
            case OK -> {
                UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService
                        .loadUserByUsername(String.valueOf(tc.userId()));

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            case EXPIRED -> {
                req.setAttribute("jwt_error", "ACCESS_TOKEN_EXPIRED"); 
                break;
            }
            case EMPTY -> {req.setAttribute("jwt_error", "NO_TOKEN"); 

                // Header 로그 찍기
                Enumeration<String> headerNames11 = req.getHeaderNames();
                while (headerNames11.hasMoreElements()) {
                    String headerName = headerNames11.nextElement();
                    String headerValue = req.getHeader(headerName);
                    
                log.warn("[HEADER] {} = {}", headerName, headerValue);
                }

                // Parameter 로그 찍기
                req.getParameterMap().forEach((k, v) ->
                log.warn("[PARAM] " + k + " = " + Arrays.toString(v))                  
                );

                // Attribute 로그 찍기
                Enumeration<String> attrNames = req.getAttributeNames();
                while (attrNames.hasMoreElements()) {
                    String attrName = attrNames.nextElement();
                    Object attrValue = req.getAttribute(attrName);
                    log.warn("[ATTR] " + attrName + " = " + attrValue);
                }
                break;
            }
            case INVALID_SIGNATURE -> req.setAttribute("jwt_error", "INVALID_SIGNATURE");
            case MALFORMED -> req.setAttribute("jwt_error", "MALFORMED");
            case UNSUPPORTED -> req.setAttribute("jwt_error", "UNSUPPORTED");
            default -> req.setAttribute("jwt_error", "INVALID_TOKEN");
        }
        fc.doFilter(req, res);
    }
}
