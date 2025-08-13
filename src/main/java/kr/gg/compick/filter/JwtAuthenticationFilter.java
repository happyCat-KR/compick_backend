package kr.gg.compick.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.gg.compick.test.NewJwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final NewJwtProvider jwtProvider;

    /** 인증 없이 통과시킬 경로들 (화이트리스트) */
    private static final List<String> WHITELIST = List.of(
        "/api/member/login",
        "/api/member/regist"
    );
    private final AntPathMatcher matcher = new AntPathMatcher();

    /** 새로 발급한 Access Token을 응답에 담을 헤더 이름 */
    public static final String NEW_ACCESS_HEADER = "X-New-Access-Token";

    /** 클라이언트가 Refresh Token을 담아줄 요청 헤더 이름 */
    public static final String REFRESH_HEADER    = "X-Refresh-Token";

    /** 화이트리스트 경로는 필터를 타지 않도록 설정 */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return WHITELIST.stream().anyMatch(p -> matcher.match(p, uri));
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        // 1) 요청 헤더에서 Authorization 값 추출
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            // 2) Authorization 없으면 바로 다음 필터로 넘김 (권한 체크는 Security에서 처리)
            if (StringUtils.hasText(authHeader) || authHeader.startsWith("Bearer ")) {
                chain.doFilter(request, response);
                return;
            }

            // 3) "Bearer " 다음의 실제 Access Token 추출
            String access = authHeader.substring(7);

            // 4) Access Token 유효 → SecurityContext에 인증 정보 세팅 후 다음 필터로
            if (jwtProvider.validateToken(access)) {
                String username = jwtProvider.getUsername(access);
                setAuth(username);
                chain.doFilter(request, response);
                return;
            }

            // 5) Access Token이 만료된 경우 → Refresh Token으로 재발급 시도
            if (jwtProvider.isAccessExpired(access)) {

                // // 5-3) Access(만료 허용)와 Refresh의 subject 일치 여부 검증
                String subjectFromAccess = jwtProvider.getUsernameFromAccessAllowExpired(access);

                String newAccess = jwtProvider.createAccessToken(subjectFromAccess);
                log.info("New Access Token created for user: {}", newAccess);

                // 5-5) 새 Access Token으로 인증 컨텍스트 세팅
                setAuth(subjectFromAccess);

                // 5-6) 새 Access Token을 응답 헤더에 담아 프론트에 전달
                response.setHeader("AUTH", newAccess);

                // 5-7) CORS 환경에서 프론트가 커스텀 헤더 읽을 수 있게 설정
                //response.setHeader("Access-Control-Expose-Headers", NEW_ACCESS_HEADER);

                // 5-8) 다음 필터로 진행
                chain.doFilter(request, response);
                return;
            }

            // 6) 위조, 형식 불일치 등 → 401 반환
            unauthorized(response, "INVALID_ACCESS", "Access token invalid");

        } catch (Exception e) {
            // 7) 토큰 파싱 중 예외 발생 시 401 반환
            log.error("JWT filter error: {}", e.getMessage(), e);
            unauthorized(response, "JWT_ERROR", "JWT processing error");
        }
    }

    /** SecurityContext에 인증 정보 세팅 */
    private void setAuth(String username) {
        UsernamePasswordAuthenticationToken auth =
            new UsernamePasswordAuthenticationToken(username, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    /** 인증 실패 시 401 응답 처리 */
    private void unauthorized(HttpServletResponse res, String code, String msg) throws IOException {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setContentType("application/json;charset=UTF-8");
        res.getWriter().write("{\"code\":\"" + code + "\",\"message\":\"" + msg + "\"}");
        res.getWriter().flush();
    }
}
