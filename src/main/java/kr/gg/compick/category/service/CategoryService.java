package kr.gg.compick.category.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.gg.compick.category.dao.CategoryRepository;
import kr.gg.compick.domain.League;
import kr.gg.compick.domain.Sport;
import kr.gg.compick.domain.board.Category;
import kr.gg.compick.match.dao.LeagueRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final LeagueRepository leagueRepository;
    @Transactional
    public Category getOrCreateCategory(String sportName, String leagueNickname) {
        String sportCode = sportName.substring(0, 2).toLowerCase();
        Long leagueId;

        switch (leagueNickname) {
            case "all": leagueId = 0L; break;
            case "laliga": leagueId = 1L; break;
            case "ucl": leagueId = 2L; break;
            case "epl": leagueId = 3L; break;
            case "kbo": leagueId = 4L; break;
            case "ufc": leagueId = 5L; break;
            default: throw new IllegalArgumentException("지원하지 않는 리그: " + leagueNickname);
        }

        String categoryIdx = sportCode + leagueId;

        // ✅ DB에서 먼저 조회
       return categoryRepository.findById(categoryIdx)
            .orElseGet(() -> {
                // 없으면 새로 생성
                League league = leagueRepository.findById(Long.valueOf(leagueId))
                        .orElseThrow(() -> new IllegalArgumentException("리그를 찾을 수 없습니다: " + leagueNickname));

                Sport sport = league.getSport(); // League가 Sport와 조인되어 있다고 가정

                Category newCategory = new Category();
                newCategory.setCategoryIdx(categoryIdx);
                newCategory.setLeague(league);   // ✅ league_id 세팅
                newCategory.setSport(sport);     // ✅ sport_id 세팅

                return categoryRepository.save(newCategory);
            });
    }

}
