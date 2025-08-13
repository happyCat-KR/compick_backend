package kr.gg.compick.util;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    public void setRefreshCookie(HttpServletResponse res, String refreshJwt, long maxAgeSec) {
        ResponseCookie cookie = ResponseCookie.from("refresh", refreshJwt) // 이름 변경
            .httpOnly(true)
            .secure(false)       // HTTPS만 쓸 경우 true, 개발 중이면 false 가능
            .sameSite("None")
            .path("/")           // 전체 경로에서 접근 가능하게
            .maxAge(maxAgeSec)
            .build();
        res.addHeader("Set-Cookie", cookie.toString());
    }

    public void clearRefreshCookie(HttpServletResponse res) {
        ResponseCookie cookie = ResponseCookie.from("refresh", "") // 이름 변경
            .httpOnly(true)
            .secure(false)
            .sameSite("None")
            .path("/")
            .maxAge(0)
            .build();
        res.addHeader("Set-Cookie", cookie.toString());
    }
}
