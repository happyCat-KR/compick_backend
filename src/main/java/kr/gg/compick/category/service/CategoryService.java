package kr.gg.compick.category.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.gg.compick.category.dao.CategoryRepository;
import kr.gg.compick.domain.League;
import kr.gg.compick.domain.Sport;
import kr.gg.compick.domain.board.Category;
import kr.gg.compick.match.dao.LeagueRepository;
import lombok.RequiredArgsConstructor;
import kr.gg.compick.sport.dao.SportRepository; 

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final LeagueRepository leagueRepository;
    private final SportRepository sportRepository;
   
      @Transactional
    public Category getOrCreateCategory(String sportCode, String leagueName) {
        // ✅ Sport 엔티티 조회 (예: "SOCCER" 코드로 조회)
        Sport sport = sportRepository.findBySportCode(sportCode)
                .orElseThrow(() -> new IllegalArgumentException("Sport not found: " + sportCode));

        // ✅ League 엔티티 조회 (예: "EPL" 이름으로 조회)
        League league = leagueRepository.findByLeagueName(leagueName)
                .orElseThrow(() -> new IllegalArgumentException("League not found: " + leagueName));

        // ✅ categoryIdx 생성 규칙
        String categoryIdx = sport.getSportCode().substring(0, 2).toUpperCase() 
                           + "_" + league.getLeagueName().toUpperCase();

        // ✅ DB에 이미 존재하면 반환, 없으면 새로 생성
        return categoryRepository.findById(categoryIdx)
                .orElseGet(() -> {
                    Category newCategory = Category.builder()
                            .categoryIdx(categoryIdx)
                            .sport(sport)   // ✅ 엔티티 넣기
                            .league(league) // ✅ 엔티티 넣기
                            .build();
                    return categoryRepository.save(newCategory);
                });
    }

}
