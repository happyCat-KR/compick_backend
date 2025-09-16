package kr.gg.compick.domain.board;

import jakarta.persistence.*;
import kr.gg.compick.domain.League;
import kr.gg.compick.domain.Sport;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 기본 생성자
@AllArgsConstructor // 모든 필드 생성자
@Builder
@Entity
@Table(name = "category")
@Getter
@Setter
public class Category {

    @Id
    @Column(unique = true, nullable = false)
    private String categoryIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sport_id", nullable = false)
    private Sport sport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league_id", nullable = false)
    private League league;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}