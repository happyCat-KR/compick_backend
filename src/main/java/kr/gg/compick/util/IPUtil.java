package kr.gg.compick.util;

import jakarta.servlet.http.HttpServletRequest;

public class IPUtil {

    private IPUtil() {}

    public static String getClientIp(HttpServletRequest req){
        String h = req.getHeader("X-Forwarded-For");
        if(h != null && !h.isBlank()){
            return h.split(",")[0].trim();
        }
        h = req.getHeader("X-Real-IP");
        if(h != null && !h.isBlank()){
            return h;
        }
        return req.getRemoteAddr();

    }

}
