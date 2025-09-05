package kr.gg.compick.match.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LeagueNameMapper {
    
    
    // 조회용: 라우팅 파라미터/별칭 → DB에 저장된 league_nickname 값
    // 예) "epl", "premierleague", "premier-league" → "premier-league"    
    private static final Map<String, String> ALIAS_TO_DB_NICK;
    private static final Map<String, String> DB_TO_CODE;
    
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
         // DB 닉네임 → 대표 코드 (프론트 라우팅용)
        Map<String, String> n = new HashMap<>();
        n.put("영국 프리미어 리그", "epl");
        n.put("스페인 라리가", "laliga");
        n.put("유럽 챔피언스 리그", "ucl");

        DB_TO_CODE = Collections.unmodifiableMap(n);
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
     /** DB 닉네임 → 프론트 코드 변환 */
    public static String toCode(String dbNickname) {
        if (dbNickname == null) return null;
        return DB_TO_CODE.getOrDefault(dbNickname, dbNickname);
    }
   
}