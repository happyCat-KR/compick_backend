package kr.gg.compick.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vote_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteType {
    @Id
    @Column(name = "vote_code")
    private String voteCode;

    private String description;
}