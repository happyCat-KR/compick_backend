package kr.gg.compick.domain.user;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "matchtag")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matchtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matchtag_idx")
    private Long matchtagIdx;

    @Column(name = "matchtag_name", nullable = false, unique = true, length = 50)
    private String matchtagName;

    @Column(name = "matchtag_at")
    private LocalDateTime matchtagAt;

    @PrePersist
    protected void onCreate() {
        this.matchtagAt = LocalDateTime.now();
    }

}