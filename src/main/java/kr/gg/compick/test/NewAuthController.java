package kr.gg.compick.test;

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import kr.gg.compick.test.service.NewAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Slf4j
public class NewAuthController {
    private final NewAuthService authService;


    //로그인했을때, jwt 토큰 발급 기능
    @GetMapping("/login")
    public void login(@RequestParam("userId") String userId,
    @RequestParam("password") String password
    ,HttpServletResponse response) {
        authService.login(userId, password, response);
    }


    //토큰이 발급 된 후!!!
    //api 사용 할 때, 토큰을 헤더에 담아서 요청
    //(여기 오기 전, Filter에서 처리 ) 토큰을 헤더에 담아서 요청하면, 토큰을 검증하고, 유효한 토큰이면, 해당 api를 사용 가능
    @PostMapping("/regist")
    @GetMapping("/test")
    public String test(@RequestParam("test") String accessToken) {
        log.info("왔니");
        return "Access Token: " + accessToken;
    }
}
