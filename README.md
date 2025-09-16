# Compick Backend

스포츠 경기 정보를 제공하는 Spring Boot 백엔드 애플리케이션입니다.

## 주요 기능

### 홈 API (새로 추가)
홈아이콘을 눌렀을 때 모든 스포츠와 모든 리그의 경기들을 나열하는 기능입니다.

#### 엔드포인트

1. **모든 경기 조회 (기본: 이번 주 월~일)**
   ```
   GET /api/home/matches
   ```
   
   **파라미터 (선택사항):**
   - `start`: 시작 날짜/시간 (ISO 형식: 2024-01-01T00:00:00)
   - `end`: 종료 날짜/시간 (ISO 형식: 2024-01-01T23:59:59)

2. **오늘 경기만 조회**
   ```
   GET /api/home/matches/today
   ```

3. **이번 주 경기 조회**
   ```
   GET /api/home/matches/this-week
   ```

4. **캘린더 월별 조회 (FullCalendar 월 그리드 대응)**
   ```
   GET /api/home/matches/monthly?year=2024&month=1
   ```

5. **특정 날짜의 경기 조회**
   ```
   GET /api/home/matches/date?date=2024-01-01
   GET /api/home/matches/date/2024/1/1
   ```

### All API (새로 추가)
스포츠 레벨에서 `all`을 지원하여 모든 스포츠와 모든 리그를 조회할 수 있는 기능입니다.

#### 엔드포인트

1. **모든 스포츠의 모든 리그 경기 조회**
   ```
   GET /api/all/matches
   GET /api/all/matches/today
   GET /api/all/matches/this-week
   GET /api/all/matches/monthly?year=2024&month=1
   GET /api/all/matches/date?date=2024-01-01
   GET /api/all/matches/date/2024/1/1
   ```

2. **특정 스포츠의 모든 리그 경기 조회**
   ```
   GET /api/all/{sport}/matches
   GET /api/all/{sport}/matches/today
   GET /api/all/{sport}/matches/this-week
   GET /api/all/{sport}/matches/monthly?year=2024&month=1
   GET /api/all/{sport}/matches/date?date=2024-01-01
   ```

3. **기존 API에서 스포츠 레벨 all 지원**
   ```
   GET /api/all/{league}/matches
   GET /api/all/{league}/matches?start=2024-01-01T00:00:00&end=2024-01-31T23:59:59
   GET /api/all/{league}/matches?year=2024&month=1
   GET /api/all/{league}/matches/{matchId}
   ```

#### 응답 예시
```json
[
  {
    "matchId": 1,
    "sport": "축구",
    "leagueNickname": "k-league",
    "leagueName": "K리그1",
    "homeTeamName": "울산현대",
    "homeTeamLogo": "https://example.com/ulsan.png",
    "homeScore": 2,
    "awayTeamName": "전북현대",
    "awayTeamLogo": "https://example.com/jeonbuk.png",
    "awayScore": 1,
    "startTime": "2024-01-01T19:00:00",
    "matchStatus": "경기종료"
  }
]
```

### 기존 API
- 경기 조회: `/api/{sport}/{league}/matches`
- 순위 조회: `/api/{sport}/{league}/rank`
- 사용자 관리: `/api/users`
- 인증: `/api/verification`

### Jenkinsfile-test
- test1
- test2
- test3: ssh agent 설치

### test1