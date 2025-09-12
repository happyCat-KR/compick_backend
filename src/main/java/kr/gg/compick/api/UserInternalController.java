package kr.gg.compick.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.gg.compick.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/internal/user")
@RequiredArgsConstructor
public class UserInternalController {
    private final UserRepository userRepository;

    @GetMapping("/{id}/exists")
    public boolean exists(@PathVariable("id") Long id) {
        System.out.println(id);
        return userRepository.existsById(id);
    }
}
