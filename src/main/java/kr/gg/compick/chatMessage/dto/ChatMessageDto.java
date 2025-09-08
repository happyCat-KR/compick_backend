package kr.gg.compick.chatMessage.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    private Long messageId;
    private String content;
    private Long userIdx;
    private String nickname;
    private LocalDateTime createdSt;
}
