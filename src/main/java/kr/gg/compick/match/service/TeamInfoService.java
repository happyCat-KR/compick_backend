package kr.gg.compick.match.service;

import kr.gg.compick.domain.TeamInfo;
import kr.gg.compick.match.dao.TeamInfoRepository;
import kr.gg.compick.match.dto.TeamInfoDto;
import kr.gg.compick.match.util.TeamNameMapper;
import kr.gg.compick.util.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamInfoService {
    
    private final TeamInfoRepository teamInfoRepository;
    

    
    /**
     * 모든 팀 정보 조회 (팀명 순으로 정렬)
     */
    public ResponseData<List<TeamInfoDto>> getAllTeams() {
        try {
            List<TeamInfo> teams = teamInfoRepository.findAllOrderByTeamName();
            List<TeamInfoDto> teamDtos = teams.stream()
                    .map(TeamInfoDto::fromEntity)
                    .collect(Collectors.toList());
            
            return ResponseData.success("팀 정보 조회 성공", teamDtos);
        } catch (Exception e) {
            log.error("팀 정보 조회 중 오류 발생", e);
            return ResponseData.error(500, "팀 정보 조회 실패: " + e.getMessage());
        }
    }
    
    /**
     * 팀 ID로 팀 정보 조회
     */
    public ResponseData<TeamInfoDto> getTeamById(Long teamId) {
        try {
            Optional<TeamInfo> teamOpt = teamInfoRepository.findById(teamId);
            
            if (teamOpt.isPresent()) {
                TeamInfoDto teamDto = TeamInfoDto.fromEntity(teamOpt.get());
                return ResponseData.success("팀 정보 조회 성공", teamDto);
            } else {
                return ResponseData.error(404, "해당 팀을 찾을 수 없습니다");
            }
        } catch (Exception e) {
            log.error("팀 정보 조회 중 오류 발생", e);
            return ResponseData.error(500, "팀 정보 조회 실패: " + e.getMessage());
        }
    }
    
    /**
     * 팀명으로 팀 정보 검색
     */
    public ResponseData<List<TeamInfoDto>> searchTeamsByName(String keyword) {
        try {
            List<TeamInfo> teams = teamInfoRepository.findByTeamNameContainingIgnoreCase(keyword);
            List<TeamInfoDto> teamDtos = teams.stream()
                    .map(TeamInfoDto::fromEntity)
                    .collect(Collectors.toList());
            
            return ResponseData.success("팀 검색 성공", teamDtos);
        } catch (Exception e) {
            log.error("팀 검색 중 오류 발생", e);
            return ResponseData.error(500, "팀 검색 실패: " + e.getMessage());
        }
    }
    
    /**
     * 특정 리그의 팀들 조회
     */
    public ResponseData<List<TeamInfoDto>> getTeamsByLeague(Long leagueId) {
        try {
            List<TeamInfo> teams = teamInfoRepository.findByLeagueId(leagueId);
            List<TeamInfoDto> teamDtos = teams.stream()
                    .map(TeamInfoDto::fromEntity)
                    .collect(Collectors.toList());
            
            return ResponseData.success("리그별 팀 정보 조회 성공", teamDtos);
        } catch (Exception e) {
            log.error("리그별 팀 정보 조회 중 오류 발생", e);
            return ResponseData.error(500, "리그별 팀 정보 조회 실패: " + e.getMessage());
        }
    }
    
    /**
     * 팀 정보 업데이트 (한국어 팀명으로 패치)
     */
    @Transactional
    public ResponseData<TeamInfoDto> updateTeamName(Long teamId, String newTeamName) {
        try {
            Optional<TeamInfo> teamOpt = teamInfoRepository.findById(teamId);
            
            if (teamOpt.isPresent()) {
                TeamInfo team = teamOpt.get();
                team.setTeamName(newTeamName);
                TeamInfo savedTeam = teamInfoRepository.save(team);
                
                TeamInfoDto teamDto = TeamInfoDto.fromEntity(savedTeam);
                return ResponseData.success("팀명 업데이트 성공", teamDto);
            } else {
                return ResponseData.error(404, "해당 팀을 찾을 수 없습니다");
            }
        } catch (Exception e) {
            log.error("팀명 업데이트 중 오류 발생", e);
            return ResponseData.error(500, "팀명 업데이트 실패: " + e.getMessage());
        }
    }
    
    /**
     * 새로운 팀 정보 추가
     */
    @Transactional
    public ResponseData<TeamInfoDto> createTeam(TeamInfoDto teamDto) {
        try {
            TeamInfo team = teamDto.toEntity();
            TeamInfo savedTeam = teamInfoRepository.save(team);
            
            TeamInfoDto savedTeamDto = TeamInfoDto.fromEntity(savedTeam);
            return ResponseData.success("팀 정보 생성 성공", savedTeamDto);
        } catch (Exception e) {
            log.error("팀 정보 생성 중 오류 발생", e);
            return ResponseData.error(500, "팀 정보 생성 실패: " + e.getMessage());
        }
    }
}
