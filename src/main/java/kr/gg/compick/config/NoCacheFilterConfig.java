package kr.gg.compick.config;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NoCacheFilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> noCacheFilter() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter((request, response, chain) -> {
            HttpServletResponse httpResp = (HttpServletResponse) response;
            httpResp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
            httpResp.setHeader("Pragma", "no-cache");
            httpResp.setHeader("Expires", "0");
            chain.doFilter(request, response);
        });
        registration.addUrlPatterns("/api/*"); // ✅ API 요청에만 적용
        return registration;
    }
}
