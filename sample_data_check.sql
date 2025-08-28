-- 데이터베이스에 있는 샘플 데이터 확인

-- 1. 스포츠 데이터 확인
SELECT * FROM sport;

-- 2. 리그 데이터 확인
SELECT 
    l.league_id,
    l.league_name,
    l.league_nickname,
    l.image_url,
    s.sport_name,
    s.sport_code
FROM league l
JOIN sport s ON l.sport_id = s.sport_id;

-- 3. 팀 데이터 확인 (상위 10개)
SELECT 
    team_id,
    team_name,
    image_url
FROM team_info
LIMIT 10;

-- 4. 경기 데이터 확인 (상위 10개)
SELECT 
    ms.match_id,
    sp.sport_name,
    l.league_nickname,
    l.league_name,
    th.team_name as home_team_name,
    th.image_url as home_team_logo,
    ta.team_name as away_team_name,
    ta.image_url as away_team_logo,
    COALESCE(sh.score, 0) as home_score,
    COALESCE(sa.score, 0) as away_score,
    ms.start_time,
    mc.description as match_status
FROM matches ms
JOIN team_info th ON ms.home_team_id = th.team_id
JOIN team_info ta ON ms.away_team_id = ta.team_id
JOIN league l ON ms.league_id = l.league_id
JOIN sport sp ON l.sport_id = sp.sport_id
JOIN match_status mc ON ms.status_code = mc.code
LEFT JOIN match_score sh ON sh.match_id = ms.match_id AND sh.team_id = ms.home_team_id
LEFT JOIN match_score sa ON sa.match_id = ms.match_id AND sa.team_id = ms.away_team_id
ORDER BY ms.start_time DESC
LIMIT 10;

-- 5. 경기 상태 데이터 확인
SELECT * FROM match_status;
