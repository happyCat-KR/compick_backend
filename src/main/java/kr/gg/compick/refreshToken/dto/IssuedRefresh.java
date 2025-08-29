package kr.gg.compick.refreshToken.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IssuedRefresh {
    private final String token;
    private final String jti;
    private final LocalDateTime expiresAt;
}
