package kr.gg.compick.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig   implements WebMvcConfigurer{
    /** ETag 필터 비활성화 */
    @Bean
    public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowEtagFilter() {
        FilterRegistrationBean<ShallowEtagHeaderFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new ShallowEtagHeaderFilter());
        filter.setEnabled(false);
        return filter;
    }

     @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Spring Security 기본 제공
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 업로드된 파일을 /images/board/** 로 접근 가능하게 매핑
        registry.addResourceHandler("/images/board/**")
                .addResourceLocations("file:uploads/board/");
    }
}
