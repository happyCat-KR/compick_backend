package kr.gg.compick.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class CookieUtil {
    public static String buildHttpOnlyCookie(
            String name, String value, Duration maxAge, String path, boolean secure) {
        String enc = URLEncoder.encode(value, StandardCharsets.UTF_8);
        long seconds = Math.max(0, maxAge.getSeconds());

        StringBuilder sb = new StringBuilder();
        sb.append(name).append("=").append(enc)
                .append("; Path=").append(path)
                .append("; Max-Age=").append(seconds)
                .append("; HttpOnly");

        if (secure) {
            // HTTPS: 크로스사이트 전송 허용
            sb.append("; SameSite=None; Secure");
        } else {
            // HTTP(로컬 개발): 같은 사이트로만 전송 (프록시로 same-origin 만들면 이걸로 충분)
            sb.append("; SameSite=Lax");
        }
        return sb.toString();
    }
}
