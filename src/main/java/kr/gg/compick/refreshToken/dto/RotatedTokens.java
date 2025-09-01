package kr.gg.compick.refreshToken.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RotatedTokens {
    private final String accessToken;
    private final IssuedRefresh refresh;
}
