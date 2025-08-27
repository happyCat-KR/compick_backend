package kr.gg.compick.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.gg.compick.security.MyUserDetailsService;
import kr.gg.compick.security.UserDetailsImpl;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final MyUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, MyUserDetailsService userDetailsService){
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                    FilterChain fc) throws ServletException, IOException {
        String bearer = req.getHeader("Authorization");
        String token = null;

        if(bearer != null && bearer.startsWith("Bearer ")){
            token = bearer.substring(7);
        }
        
        if(token != null && tokenProvider.validateToken(token)){
            System.out.println("hi");
            Long userIdx = tokenProvider.getUserIdxFromToken(token);
            UserDetailsImpl userDetails =
                (UserDetailsImpl) userDetailsService.loadUserByUsername(String.valueOf(userIdx));

            UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        fc.doFilter(req, res);
    }
}
