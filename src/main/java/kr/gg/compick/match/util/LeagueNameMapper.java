package kr.gg.compick.match.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LeagueNameMapper {
    
    
    // 조회용: 라우팅 파라미터/별칭 → DB에 저장된 league_nickname 값
    // 예) "epl", "premierleague", "premier-league" → "premier-league"    
    private static final Map<String, String> ALIAS_TO_DB_NICK;

    static {
        Map<String, String> m = new HashMap<>();

        // ===== soccer =====
        // EPL
        put(m, "epl", "영국 프리미어 리그");
        put(m, "premierleague", "영국 프리미어 리그");
        put(m, "premier-league", "영국 프리미어 리그");
        put(m, "pl", "영국 프리미어 리그");

        // LaLiga
        put(m, "laliga", "스페인 라리가");
        put(m, "la-liga", "스페인 라리가");
        put(m, "la liga", "스페인 라리가");
        put(m, "liga", "스페인 라리가");

        // UEFA Champions League
        put(m, "ucl", "유럽 챔피언스 리그");
        put(m, "championsleague", "유럽 챔피언스 리그");
        put(m, "champions-league", "유럽 챔피언스 리그");
        put(m, "uefa-champions-league", "유럽 챔피언스 리그");

        ALIAS_TO_DB_NICK = Collections.unmodifiableMap(m);
    }
    
     private static void put(Map<String, String> map, String key, String dbNickname) {
        map.put(normalize(key), dbNickname);
    }

    private static String normalize(String raw) {
        if (raw == null) return null;
        String k = raw.trim().toLowerCase(Locale.ROOT);
        return k.replaceAll("[\\s_]+", "-"); // "la liga" / "la_liga" -> "la-liga"
    }

    /** DB 조회용 닉네임으로 변환 (없으면 정규화된 원문 반환) */
    public static String toDbNickname(String leagueParamOrAlias) {
        if (leagueParamOrAlias == null) return null;
        String key = normalize(leagueParamOrAlias);
        // 매핑이 없으면 원문 그대로(디버깅에 도움)
        return ALIAS_TO_DB_NICK.getOrDefault(key, leagueParamOrAlias);
    }
   
}