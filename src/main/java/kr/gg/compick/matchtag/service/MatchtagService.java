package kr.gg.compick.matchtag.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.gg.compick.domain.user.Matchtag;
import kr.gg.compick.matchtag.dao.MatchtagRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchtagService {

    final MatchtagRepository matchtagRepository;

    public List<String> findAllCategoryNames() {
        return matchtagRepository.findAll().stream()
                .map(Matchtag::getMatchtagName)
                .collect(Collectors.toList());
    }
}
