package kr.gg.compick.match.service.matchFilter;

public interface LookupService {
    /*
        api/sport/league 값 사용하면서 슬러그와 id 매핑하는 기능
    */
    
    Long sportIdOf(String sportSlug);   // all이면 null 반환
    Long leagueIdOf(String leagueSlug); // all이면 null 반환
}
