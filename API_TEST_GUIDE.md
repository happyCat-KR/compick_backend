# API 테스트 가이드

## 홈 API 테스트

### 1. 모든 경기 조회 (기본: 이번 주 월~일)

```bash
# 기본 조회 (이번 주 월~일)
curl -X GET "http://localhost:8080/api/home/matches"

# 특정 기간 조회
curl -X GET "http://localhost:8080/api/matches?start=2024-01-01T00:00:00&end=2024-01-31T23:59:59"
```

### 2. 캘린더 월별 조회

```bash
# 2024년 1월 캘린더 조회 (월~일 그리드)
curl -X GET "http://localhost:8080/api/home/matches/monthly?year=2024&month=1"
```

### 3. 특정 날짜 경기 조회

```bash
# 2024년 1월 1일 경기 조회 (쿼리 파라미터)
curl -X GET "http://localhost:8080/api/home/matches/date?date=2024-01-01"

# 2024년 1월 1일 경기 조회 (경로 변수)
curl -X GET "http://localhost:8080/api/home/matches/date/2024/1/1"
```

### 2. 오늘 경기만 조회

```bash
curl -X GET "http://localhost:8080/api/home/matches/today"
```

### 3. 이번 주 경기 조회

```bash
curl -X GET "http://localhost:8080/api/home/matches/this-week"
```

## All API 테스트

### 1. 모든 스포츠의 모든 리그 경기 조회

```bash
# 기본 조회 (이번 주 월~일)
curl -X GET "http://localhost:8080/api/all/matches"

# 특정 기간 조회
curl -X GET "http://localhost:8080/api/all/matches?start=2024-01-01T00:00:00&end=2024-01-31T23:59:59"

# 오늘 경기만
curl -X GET "http://localhost:8080/api/all/matches/today"

# 이번 주 경기만
curl -X GET "http://localhost:8080/api/all/matches/this-week"

# 캘린더 월별 조회
curl -X GET "http://localhost:8080/api/all/matches/monthly?year=2024&month=1"

# 특정 날짜 경기 조회
curl -X GET "http://localhost:8080/api/all/matches/date?date=2024-01-01"
curl -X GET "http://localhost:8080/api/all/matches/date/2024/1/1"
```

### 2. 특정 스포츠의 모든 리그 경기 조회

```bash
# 축구의 모든 리그 경기 조회
curl -X GET "http://localhost:8080/api/all/soccer/matches"

# 야구의 모든 리그 경기 조회 (오늘)
curl -X GET "http://localhost:8080/api/all/baseball/matches/today"

# 농구의 모든 리그 경기 조회 (이번 주)
curl -X GET "http://localhost:8080/api/all/basketball/matches/this-week"

# 축구의 모든 리그 - 캘린더 월별 조회
curl -X GET "http://localhost:8080/api/all/soccer/matches/monthly?year=2024&month=1"

# 축구의 모든 리그 - 특정 날짜 경기 조회
curl -X GET "http://localhost:8080/api/all/soccer/matches/date?date=2024-01-01"
```

### 3. 기존 API에서 스포츠 레벨 all 지원

```bash
# 모든 스포츠의 K리그 경기 조회 (기존 API 구조 유지)
curl -X GET "http://localhost:8080/api/all/k-league/matches"

# 모든 스포츠의 특정 경기 상세 조회
curl -X GET "http://localhost:8080/api/all/k-league/matches/123"
```

## 응답 예시

### 성공 응답 (200 OK)
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
  },
  {
    "matchId": 2,
    "sport": "야구",
    "leagueNickname": "kbo",
    "leagueName": "KBO리그",
    "homeTeamName": "삼성라이온즈",
    "homeTeamLogo": "https://example.com/samsung.png",
    "homeScore": 5,
    "awayTeamName": "두산베어스",
    "awayTeamLogo": "https://example.com/doosan.png",
    "awayScore": 3,
    "startTime": "2024-01-01T18:30:00",
    "matchStatus": "경기중"
  }
]
```

### 빈 결과 (200 OK)
```json
[]
```

## 정렬 순서

응답은 다음 순서로 정렬됩니다:
1. 경기 시작 시간 (오름차순)
2. 스포츠 이름 (오름차순)
3. 리그 닉네임 (오름차순)

## 에러 응답

### 잘못된 날짜 형식 (400 Bad Request)
```json
{
  "timestamp": "2024-01-01T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Failed to convert value of type 'java.lang.String' to required type 'java.time.LocalDateTime'"
}
```

## 테스트 시나리오

1. **기본 테스트**: 서버 실행 후 기본 엔드포인트 호출
2. **날짜 범위 테스트**: 다양한 날짜 범위로 테스트
3. **빈 결과 테스트**: 데이터가 없는 기간으로 테스트
4. **대용량 데이터 테스트**: 많은 경기가 있는 기간으로 테스트
5. **성능 테스트**: 응답 시간 측정

## 팀 정보 API 테스트

### 1. 모든 팀 정보 조회

```bash
# 축구 K리그의 모든 팀 정보 조회
curl -X GET "http://localhost:8080/api/soccer/k-league/matches/teams"

# 야구 KBO의 모든 팀 정보 조회
curl -X GET "http://localhost:8080/api/baseball/kbo/matches/teams"
```

### 2. 특정 팀 정보 조회

```bash
# 팀 ID로 팀 정보 조회
curl -X GET "http://localhost:8080/api/soccer/k-league/matches/teams/1"
```

### 3. 팀명으로 검색

```bash
# 팀명에 "현대"가 포함된 팀들 검색
curl -X GET "http://localhost:8080/api/soccer/k-league/matches/teams/search?keyword=현대"
```

### 4. 팀명 업데이트 (한국어 팀명으로 패치)

```bash
# 팀명을 한국어로 업데이트
curl -X PATCH "http://localhost:8080/api/soccer/k-league/matches/teams/1/name" \
  -H "Content-Type: application/json" \
  -d '{"newTeamName": "울산현대FC"}'
```

### 5. 새로운 팀 정보 추가

```bash
# 새로운 팀 정보 추가
curl -X POST "http://localhost:8080/api/soccer/k-league/matches/teams" \
  -H "Content-Type: application/json" \
  -d '{
    "teamId": 999,
    "teamName": "새로운팀",
    "imageUrl": "https://example.com/new-team.png"
  }'
```

## 팀 정보 응답 예시

### 성공 응답 (200 OK)
```json
{
  "code": 200,
  "message": "팀 정보 조회 성공",
  "data": [
    {
      "teamId": 1,
      "teamName": "울산현대",
      "imageUrl": "https://example.com/ulsan.png"
    },
    {
      "teamId": 2,
      "teamName": "전북현대",
      "imageUrl": "https://example.com/jeonbuk.png"
    },
    {
      "teamId": 3,
      "teamName": "맨유",
      "imageUrl": "https://example.com/manchester-united.png"
    },
    {
      "teamId": 4,
      "teamName": "바르셀로나",
      "imageUrl": "https://example.com/barcelona.png"
    }
  ]
}
```

### 팀명 업데이트 성공 응답
```json
{
  "code": 200,
  "message": "팀명 업데이트 성공",
  "data": {
    "teamId": 1,
    "teamName": "울산현대",
    "imageUrl": "https://example.com/ulsan.png"
  }
}
```

## 개발 환경에서 테스트

```bash
# 서버 실행
./gradlew bootRun

# 별도 터미널에서 API 테스트
curl -X GET "http://localhost:8080/api/home/matches" | jq

# 팀 정보 API 테스트
curl -X GET "http://localhost:8080/api/soccer/k-league/matches/teams" | jq
```
