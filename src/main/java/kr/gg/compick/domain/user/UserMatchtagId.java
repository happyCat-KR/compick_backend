package kr.gg.compick.domain.user;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class UserMatchtagId implements Serializable {
    private Long userIdx;
    private Long matchtagIdx;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserMatchtagId)) return false;
        UserMatchtagId that = (UserMatchtagId) o;
        return Objects.equals(userIdx, that.userIdx) &&
               Objects.equals(matchtagIdx, that.matchtagIdx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userIdx, matchtagIdx);
    }
}