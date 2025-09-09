package kr.gg.compick.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
// hi
@RestController
public class LBController {

    // application.yml 또는 환경변수에서 읽기
    @Value("${app.instance:app-unknown}")
    private String instanceName;

    @GetMapping("/api/lb/who")
    public Map<String, String> whoAmI() {
        return Map.of("instance", instanceName);
    }
}
