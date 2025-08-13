package kr.gg.compick.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// kr.gg.compick.config.WebConfig (또는 Security/Cors 설정)
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry reg) {
        reg.addMapping("/**")
                .allowedOriginPatterns("http://localhost:3000", "https://your.app.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                //.exposedHeaders("X-New-Access-Token")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
