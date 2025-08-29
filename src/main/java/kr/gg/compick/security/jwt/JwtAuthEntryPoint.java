package kr.gg.compick.security.jwt;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint{
    @Override
    public void commence(
        HttpServletRequest req,
        HttpServletResponse res,
        AuthenticationException ex
        ) throws IOException{
            String err = (String) req.getAttribute("jwt_error");
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json;charset=UTF-8");
            res.getWriter().write("{\"code\":401,\"msg\":\""+(err==null?"UNAUTHORIZED":err)+"\"}");

    }
}
