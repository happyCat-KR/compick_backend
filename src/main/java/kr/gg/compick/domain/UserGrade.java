package kr.gg.compick.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_grade")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserGrade {
    @Id
    @Column(name = "user_idx")
    private Long userIdx;

    @Column(name = "total_votes")
    private int totalVotes;

    @Column(name = "correct_cnt")
    private int correctCnt;

    private String grade;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}