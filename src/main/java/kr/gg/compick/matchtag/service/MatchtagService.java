package kr.gg.compick.matchtag.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import kr.gg.compick.domain.board.Board;
import kr.gg.compick.domain.match.Matches;
import kr.gg.compick.domain.user.Matchtag;
import kr.gg.compick.match.dao.MatchRepository;
import kr.gg.compick.match.dto.MatchTagDTO;
import kr.gg.compick.matchtag.dao.MatchtagRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchtagService {

    private final MatchtagRepository matchtagRepository;
    private final MatchRepository matchesRepository;

    @Transactional
    public void saveMatchtags(Board savedBoard, List<MatchTagDTO> matchTagDTOs) {
        if (matchTagDTOs == null || matchTagDTOs.isEmpty()) return;

        for (MatchTagDTO dto : matchTagDTOs) {
            Matches match = matchesRepository.findById(dto.getMatchId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 matchId: " + dto.getMatchId()));

            Matchtag matchtag = Matchtag.builder()
                    .board(savedBoard)
                    .match(match)
                    .build();

            matchtagRepository.save(matchtag);
        }
        
    }
    @Transactional(readOnly = true)
    public List<Matches> getMatchesByBoardId(Long boardId) {
        return matchtagRepository.findByBoardIdWithMatch(boardId).stream()
                .map(Matchtag::getMatch) // Matchtag → Matches 변환
                .collect(Collectors.toList());
    }
}
