package kr.gg.compick.match.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 팀명을 한국에서 통칭하는 구단명으로 변환하는 유틸리티 클래스
 */
public class TeamNameMapper {
    
    private static final Map<String, String> TEAM_NAME_MAP = new HashMap<>();
    
    static {
    
        // 영국 프리미어 리그 (EPL) 팀명 매핑
        TEAM_NAME_MAP.put("Manchester United", "맨유");
        TEAM_NAME_MAP.put("Manchester City", "맨시티");
        TEAM_NAME_MAP.put("Bournemouth", "본머스");
        TEAM_NAME_MAP.put("Liverpool", "리버풀");
        TEAM_NAME_MAP.put("Chelsea", "첼시");
        TEAM_NAME_MAP.put("Arsenal", "아스널");
        TEAM_NAME_MAP.put("Tottenham Hotspur", "토트넘");
        TEAM_NAME_MAP.put("Newcastle United", "뉴캐슬");
        TEAM_NAME_MAP.put("Aston Villa", "애스턴 빌라");
        TEAM_NAME_MAP.put("Brighton & Hove Albion", "브라이튼");
        TEAM_NAME_MAP.put("West Ham United", "웨스트햄");
        TEAM_NAME_MAP.put("Crystal Palace", "크리스탈 팰리스");
        TEAM_NAME_MAP.put("Fulham", "풀럼");
        TEAM_NAME_MAP.put("Brentford", "브렌트포드");
        TEAM_NAME_MAP.put("Wolverhampton Wanderers", "울버햄튼");
        TEAM_NAME_MAP.put("Everton", "에버튼");
        TEAM_NAME_MAP.put("Nottingham Forest", "노팅엄 포레스트");
        TEAM_NAME_MAP.put("Burnley", "번리");
        TEAM_NAME_MAP.put("Luton Town", "루턴 타운");
        TEAM_NAME_MAP.put("Sheffield United", "셰필드 유나이티드");
        TEAM_NAME_MAP.put("Leicester City", "레스터");
        TEAM_NAME_MAP.put("Leeds United", "리즈");
        TEAM_NAME_MAP.put("Southampton", "사우샘프턴");
        TEAM_NAME_MAP.put("Watford", "왓포드");
        TEAM_NAME_MAP.put("Norwich City", "노리치");
        TEAM_NAME_MAP.put("Bournemouth", "본머스");
        TEAM_NAME_MAP.put("Cardiff City", "카디프");
        TEAM_NAME_MAP.put("Huddersfield Town", "허더스필드");
        TEAM_NAME_MAP.put("Swansea City", "스완지");
        TEAM_NAME_MAP.put("Stoke City", "스토크");
        TEAM_NAME_MAP.put("West Bromwich Albion", "웨스트브롬");
        TEAM_NAME_MAP.put("Middlesbrough", "미들즈브러");
        TEAM_NAME_MAP.put("Sunderland", "선덜랜드");
        TEAM_NAME_MAP.put("Hull City", "헐");
        TEAM_NAME_MAP.put("Reading", "레딩");
        TEAM_NAME_MAP.put("Derby County", "더비");
        TEAM_NAME_MAP.put("Birmingham City", "버밍엄");
        TEAM_NAME_MAP.put("Blackburn Rovers", "블랙번");
        TEAM_NAME_MAP.put("Bolton Wanderers", "볼튼");
        TEAM_NAME_MAP.put("Wigan Athletic", "위건");
        TEAM_NAME_MAP.put("Portsmouth", "포츠머스");
        TEAM_NAME_MAP.put("Charlton Athletic", "찰튼");
        TEAM_NAME_MAP.put("Coventry City", "코벤트리");
        TEAM_NAME_MAP.put("Bradford City", "브래드포드");
        TEAM_NAME_MAP.put("Barnsley", "반슬리");
        TEAM_NAME_MAP.put("Oldham Athletic", "올덤");
        TEAM_NAME_MAP.put("Swindon Town", "스윈던");
        TEAM_NAME_MAP.put("Queens Park Rangers", "QPR");
        TEAM_NAME_MAP.put("Millwall", "밀월");
        TEAM_NAME_MAP.put("Ipswich Town", "입스위치");
        TEAM_NAME_MAP.put("Peterborough United", "피터버러");
        TEAM_NAME_MAP.put("Northampton Town", "노샘프턴");
        TEAM_NAME_MAP.put("Milton Keynes Dons", "MK 돈스");
        TEAM_NAME_MAP.put("Colchester United", "콜체스터");
        TEAM_NAME_MAP.put("Southend United", "사우스엔드");
        TEAM_NAME_MAP.put("Gillingham", "질링엄");
        TEAM_NAME_MAP.put("Leyton Orient", "레이튼 오리엔트");
        TEAM_NAME_MAP.put("Barnet", "바넷");
        TEAM_NAME_MAP.put("Dagenham & Redbridge", "데이건엄");
        TEAM_NAME_MAP.put("Stevenage", "스티브니지");
        TEAM_NAME_MAP.put("Crawley Town", "크롤리");
        TEAM_NAME_MAP.put("AFC Wimbledon", "윔블던");
        TEAM_NAME_MAP.put("Sutton United", "서튼");
        TEAM_NAME_MAP.put("Bromley", "브롬리");
        TEAM_NAME_MAP.put("Boreham Wood", "보어햄 우드");
        TEAM_NAME_MAP.put("Wealdstone", "윌드스톤");
        TEAM_NAME_MAP.put("Maidenhead United", "메이든헤드");
        TEAM_NAME_MAP.put("Slough Town", "슬로우");
        TEAM_NAME_MAP.put("Hemel Hempstead Town", "헤멜 헴프스테드");
        TEAM_NAME_MAP.put("St Albans City", "세인트 앨번스");
        TEAM_NAME_MAP.put("Dartford", "다트포드");
        TEAM_NAME_MAP.put("Ebbsfleet United", "엡스플릿");
        TEAM_NAME_MAP.put("Dover Athletic", "도버");
        TEAM_NAME_MAP.put("Maidstone United", "메이드스톤");
        TEAM_NAME_MAP.put("Tonbridge Angels", "톤브리지");
        TEAM_NAME_MAP.put("Hastings United", "헤이스팅스");
        TEAM_NAME_MAP.put("Folkestone Invicta", "폴크스톤");
        TEAM_NAME_MAP.put("Margate", "마게이트");
        TEAM_NAME_MAP.put("Canvey Island", "캔비 아일랜드");
        TEAM_NAME_MAP.put("Billericay Town", "빌러리케이");
        TEAM_NAME_MAP.put("Concord Rangers", "콩코드");
        TEAM_NAME_MAP.put("Braintree Town", "브레인트리");
        TEAM_NAME_MAP.put("Chelmsford City", "첼름스포드");
        TEAM_NAME_MAP.put("Bishop's Stortford", "비숍스 스토트포드");
        TEAM_NAME_MAP.put("St Neots Town", "세인트 니오츠");
        TEAM_NAME_MAP.put("Kettering Town", "케터링");
        TEAM_NAME_MAP.put("Corby Town", "코비");
        TEAM_NAME_MAP.put("Nuneaton Town", "누니턴");
        TEAM_NAME_MAP.put("Leamington", "리밍턴");
        TEAM_NAME_MAP.put("Stratford Town", "스트랫포드");
        TEAM_NAME_MAP.put("Redditch United", "레디치");
        TEAM_NAME_MAP.put("Alvechurch", "앨브처치");
        TEAM_NAME_MAP.put("Bromsgrove Sporting", "브롬스그로브");
        TEAM_NAME_MAP.put("Hednesford Town", "헤드네스포드");
        TEAM_NAME_MAP.put("Stafford Rangers", "스태퍼드");
        TEAM_NAME_MAP.put("Stourbridge", "스타우어브리지");
        TEAM_NAME_MAP.put("Halesowen Town", "헤일스오웬");
        TEAM_NAME_MAP.put("Rushall Olympic", "러샬");
        TEAM_NAME_MAP.put("Chasetown", "체이스타운");
        TEAM_NAME_MAP.put("Walsall Wood", "월솔 우드");
        TEAM_NAME_MAP.put("Lye Town", "라이");
        TEAM_NAME_MAP.put("Tividale", "티비데일");
        TEAM_NAME_MAP.put("Sporting Khalsa", "스포팅 칼사");
        TEAM_NAME_MAP.put("Wolverhampton Casuals", "울버햄튼 캐주얼스");
        TEAM_NAME_MAP.put("Bilston Town", "빌스턴");
        TEAM_NAME_MAP.put("Wednesfield", "웬즈필드");
        TEAM_NAME_MAP.put("Willenhall Town", "윌렌홀");
        TEAM_NAME_MAP.put("Bloxwich United", "블록스위치");
        TEAM_NAME_MAP.put("Pelsall Villa", "펠솔 빌라");
        TEAM_NAME_MAP.put("Heath Hayes", "히스 헤이즈");
        TEAM_NAME_MAP.put("Cannock Chase", "캐녹 체이스");
        TEAM_NAME_MAP.put("Rocester", "로세스터");
        TEAM_NAME_MAP.put("Uttoxeter Town", "어톡서터");
        TEAM_NAME_MAP.put("Gresley", "그레슬리");
        TEAM_NAME_MAP.put("Belper Town", "벨퍼");
        TEAM_NAME_MAP.put("Matlock Town", "매틀록");
        TEAM_NAME_MAP.put("Buxton", "벅스턴");
        TEAM_NAME_MAP.put("Worksop Town", "워크솝");
        TEAM_NAME_MAP.put("Retford United", "레트포드");
        TEAM_NAME_MAP.put("Gainsborough Trinity", "게인즈버러");
        TEAM_NAME_MAP.put("Scarborough Athletic", "스카보로 애슬레틱");
        TEAM_NAME_MAP.put("Whitby Town", "휘트비");
        TEAM_NAME_MAP.put("Morpeth Town", "모르페스");
        TEAM_NAME_MAP.put("Dunston UTS", "던스턴");
        TEAM_NAME_MAP.put("Consett", "콘셋");
        TEAM_NAME_MAP.put("Whitley Bay", "휘틀리 베이");
        TEAM_NAME_MAP.put("Ashington", "애싱턴");
        TEAM_NAME_MAP.put("Blyth Spartans", "블라이스");
        TEAM_NAME_MAP.put("Spennymoor Town", "스페니무어");
        TEAM_NAME_MAP.put("Bishop Auckland", "비숍 오클랜드");
        TEAM_NAME_MAP.put("Shildon", "실던");
        TEAM_NAME_MAP.put("Newton Aycliffe", "뉴턴 에이클리프");
        TEAM_NAME_MAP.put("West Auckland Town", "웨스트 오클랜드");
        TEAM_NAME_MAP.put("Tow Law Town", "토우 로");
        TEAM_NAME_MAP.put("Crook Town", "크룩");
        TEAM_NAME_MAP.put("Brandon United", "브랜던");
        TEAM_NAME_MAP.put("Esh Winning", "에시 위닝");
        TEAM_NAME_MAP.put("Evenwood Town", "이븐우드");
        TEAM_NAME_MAP.put("Willington", "윌링턴");
        TEAM_NAME_MAP.put("Stanley United", "스탠리");
        TEAM_NAME_MAP.put("Annfield Plain", "앤필드 플레인");
        TEAM_NAME_MAP.put("Horden Colliery Welfare", "호든");
        TEAM_NAME_MAP.put("Seaham Red Star", "시험");
        TEAM_NAME_MAP.put("Sunderland RCA", "선덜랜드 RCA");
        TEAM_NAME_MAP.put("Jarrow", "재로우");
        TEAM_NAME_MAP.put("Hebburn Town", "헤번");
        TEAM_NAME_MAP.put("Whickham", "휘컴");
        TEAM_NAME_MAP.put("Newcastle Benfield", "뉴캐슬 벤필드");
        TEAM_NAME_MAP.put("North Shields", "노스 실즈");
        TEAM_NAME_MAP.put("Sunderland", "선덜랜드");
        
        // 스페인 라리가 팀명 매핑
        TEAM_NAME_MAP.put("Girona FC", "지로나");
        TEAM_NAME_MAP.put("Barcelona", "바르셀로나");
        TEAM_NAME_MAP.put("Real Madrid", "레알마드리드");
        TEAM_NAME_MAP.put("Atletico Madrid", "아틀레티코 마드리드");
        TEAM_NAME_MAP.put("Athletic Bilbao", "아틀레틱 빌바오");
        TEAM_NAME_MAP.put("Real Sociedad", "레알 소시에다드");
        TEAM_NAME_MAP.put("Girona", "지로나");
        TEAM_NAME_MAP.put("Real Betis", "레알 베티스");
        TEAM_NAME_MAP.put("Las Palmas", "라스 팔마스");
        TEAM_NAME_MAP.put("Rayo Vallecano", "라요 바예카노");
        TEAM_NAME_MAP.put("Valencia", "발렌시아");
        TEAM_NAME_MAP.put("Getafe", "헤타페");
        TEAM_NAME_MAP.put("Osasuna", "오사수나");
        TEAM_NAME_MAP.put("Villarreal", "비야레알");
        TEAM_NAME_MAP.put("Mallorca", "마요르카");
        TEAM_NAME_MAP.put("Alaves", "알라베스");
        TEAM_NAME_MAP.put("Sevilla", "세비야");
        TEAM_NAME_MAP.put("Celta Vigo", "셀타 비고");
        TEAM_NAME_MAP.put("Cadiz", "카디스");
        TEAM_NAME_MAP.put("Granada", "그라나다");
        TEAM_NAME_MAP.put("Almeria", "알메리아");
        TEAM_NAME_MAP.put("Espanyol", "에스파뇰");
        TEAM_NAME_MAP.put("Levante", "레반테");
        TEAM_NAME_MAP.put("Elche", "엘체");
        TEAM_NAME_MAP.put("Huesca", "우에스카");
        TEAM_NAME_MAP.put("Valladolid", "바야돌리드");
        TEAM_NAME_MAP.put("Eibar", "에이바르");
        TEAM_NAME_MAP.put("Leganes", "레가네스");
        TEAM_NAME_MAP.put("Malaga", "말라가");
        TEAM_NAME_MAP.put("Sporting Gijon", "스포르팅 히혼");
        TEAM_NAME_MAP.put("Real Zaragoza", "레알 사라고사");
        TEAM_NAME_MAP.put("Racing Santander", "라싱 산탄데르");
        TEAM_NAME_MAP.put("Deportivo La Coruna", "데포르티보 라 코루냐");
        TEAM_NAME_MAP.put("Real Oviedo", "레알 오비에도");
        TEAM_NAME_MAP.put("Real Valladolid", "바야돌리드");
        TEAM_NAME_MAP.put("Real Betis Balompie", "레알 베티스");
        TEAM_NAME_MAP.put("Sevilla FC", "세비야");
        TEAM_NAME_MAP.put("Real Sociedad de San Sebastian", "레알 소시에다드");
        TEAM_NAME_MAP.put("Athletic Club", "아틀레틱 빌바오");
        TEAM_NAME_MAP.put("CA Osasuna", "오사수나");
        TEAM_NAME_MAP.put("Real Zaragoza", "사라고사");
        TEAM_NAME_MAP.put("Real Sporting de Gijon", "스포르팅 히혼");
        TEAM_NAME_MAP.put("Real Racing Club de Santander", "라싱 산탄데르");
        TEAM_NAME_MAP.put("Real Club Deportivo de La Coruna", "데포르티보");
        TEAM_NAME_MAP.put("Real Oviedo", "오비에도");
        TEAM_NAME_MAP.put("Real Valladolid Club de Futbol", "바야돌리드");
        TEAM_NAME_MAP.put("Real Betis Balompie", "베티스");
        TEAM_NAME_MAP.put("Sevilla Futbol Club", "세비야");
        TEAM_NAME_MAP.put("Real Sociedad de Futbol", "소시에다드");
        TEAM_NAME_MAP.put("Athletic Club de Bilbao", "빌바오");
        TEAM_NAME_MAP.put("Club Atletico Osasuna", "오사수나");
        TEAM_NAME_MAP.put("Real Zaragoza SAD", "사라고사");
        TEAM_NAME_MAP.put("Real Sporting de Gijon SAD", "스포르팅");
        TEAM_NAME_MAP.put("Real Racing Club de Santander SAD", "라싱");
        TEAM_NAME_MAP.put("Real Club Deportivo de La Coruna SAD", "데포르티보");
        TEAM_NAME_MAP.put("Real Oviedo SAD", "오비에도");
        TEAM_NAME_MAP.put("Real Valladolid Club de Futbol SAD", "바야돌리드");
        TEAM_NAME_MAP.put("Real Betis Balompie SAD", "베티스");
        TEAM_NAME_MAP.put("Sevilla Futbol Club SAD", "세비야");
        TEAM_NAME_MAP.put("Real Sociedad de Futbol SAD", "소시에다드");
        TEAM_NAME_MAP.put("Athletic Club de Bilbao SAD", "빌바오");
        TEAM_NAME_MAP.put("Club Atletico Osasuna SAD", "오사수나");
        
        // 유럽 챔피언스 리그 팀명 매핑
        TEAM_NAME_MAP.put("FC København", "코펜하겐");
        TEAM_NAME_MAP.put("Malmö FF", "말뫼");
        TEAM_NAME_MAP.put("PSV Eindhoven", "PSV");
        TEAM_NAME_MAP.put("Feyenoord", "페예노르트");
        TEAM_NAME_MAP.put("Red Bull Salzburg", "잘츠부르크");
        TEAM_NAME_MAP.put("Red Star Belgrade", "츠르베나 즈베즈다");
        TEAM_NAME_MAP.put("Dinamo Zagreb", "디나모 자그레브");
        TEAM_NAME_MAP.put("Shakhtar Donetsk", "샤흐타르 도네츠크");
        TEAM_NAME_MAP.put("Dynamo Kyiv", "디나모 키예프");
        TEAM_NAME_MAP.put("Slavia Prague", "슬라비아 프라하");
        TEAM_NAME_MAP.put("Sparta Prague", "스파르타 프라하");
        TEAM_NAME_MAP.put("Young Boys", "영 보이즈");
        TEAM_NAME_MAP.put("Basel", "바젤");
        TEAM_NAME_MAP.put("Rosenborg", "로젠보르그");
        TEAM_NAME_MAP.put("Molde", "몰데");
        TEAM_NAME_MAP.put("Bodø/Glimt", "보되/글림트");
        TEAM_NAME_MAP.put("Ludogorets Razgrad", "루도고레츠");
        TEAM_NAME_MAP.put("Ferencváros", "페렌츠바로시");
        TEAM_NAME_MAP.put("Qarabağ", "카라바흐");
        TEAM_NAME_MAP.put("Sheriff Tiraspol", "셰리프 티라스폴");
        TEAM_NAME_MAP.put("Astana", "아스타나");
        TEAM_NAME_MAP.put("APOEL", "아포엘");
        TEAM_NAME_MAP.put("Olympiacos", "올림피아코스");
        TEAM_NAME_MAP.put("PAOK", "PAOK");
        TEAM_NAME_MAP.put("AEK Athens", "AEK 아테네");
        TEAM_NAME_MAP.put("Panathinaikos", "파나티나이코스");
        TEAM_NAME_MAP.put("Galatasaray", "갈라타사라이");
        TEAM_NAME_MAP.put("Fenerbahçe", "페네르바흐체");
        TEAM_NAME_MAP.put("Beşiktaş", "베식타시");
        TEAM_NAME_MAP.put("Trabzonspor", "트라브존스포르");
        TEAM_NAME_MAP.put("Zenit Saint Petersburg", "제니트");
        TEAM_NAME_MAP.put("CSKA Moscow", "CSKA 모스크바");
        TEAM_NAME_MAP.put("Spartak Moscow", "스파르타크 모스크바");
        TEAM_NAME_MAP.put("Lokomotiv Moscow", "로코모티프 모스크바");
        TEAM_NAME_MAP.put("Krasnodar", "크라스노다르");
        TEAM_NAME_MAP.put("Rostov", "로스토프");
        TEAM_NAME_MAP.put("Kairat Almaty", "카라이트 알마티");
        TEAM_NAME_MAP.put("FC Milsami Orhei", "밀사미 오르헤이");
        TEAM_NAME_MAP.put("FC Noah", "노아");
        TEAM_NAME_MAP.put("Kairat Almaty", "카라이트 알마티");
        TEAM_NAME_MAP.put("Rubin Kazan", "루빈 카잔");
        TEAM_NAME_MAP.put("Ufa", "우파");
        TEAM_NAME_MAP.put("Arsenal", "아스날");
        TEAM_NAME_MAP.put("Akhmat Grozny", "아흐마트 그로즈니");
        TEAM_NAME_MAP.put("Ural Yekaterinburg", "우랄 예카테린부르크");
        TEAM_NAME_MAP.put("Sochi", "소치");
        TEAM_NAME_MAP.put("Khimki", "힘키");
        TEAM_NAME_MAP.put("Tambov", "탐보프");
        TEAM_NAME_MAP.put("Orenburg", "오렌부르크");
        TEAM_NAME_MAP.put("Krylia Sovetov", "크릴리야 소베토프");
        TEAM_NAME_MAP.put("Nizhny Novgorod", "니즈니 노브고로드");
        TEAM_NAME_MAP.put("Torpedo Moscow", "토르페도 모스크바");
        TEAM_NAME_MAP.put("Fakel Voronezh", "파켈 보로네시");
        TEAM_NAME_MAP.put("Baltika Kaliningrad", "발티카 칼리닌그라드");
        TEAM_NAME_MAP.put("Paris FC", "파리 FC");
        TEAM_NAME_MAP.put("Ajax", "아약스");
        TEAM_NAME_MAP.put("Porto", "포르투");
        TEAM_NAME_MAP.put("Benfica", "벤피카");
        TEAM_NAME_MAP.put("Sporting CP", "스포르팅 CP");
        TEAM_NAME_MAP.put("Braga", "브라가");
        TEAM_NAME_MAP.put("Vitoria Guimaraes", "비토리아 기마랑이스");
        TEAM_NAME_MAP.put("Boavista", "보아비스타");
        TEAM_NAME_MAP.put("Maritimo", "마리티무");
        TEAM_NAME_MAP.put("Nacional", "나시오날");
        TEAM_NAME_MAP.put("Moreirense", "모레이렌스");
        TEAM_NAME_MAP.put("Tondela", "톤델라");
        TEAM_NAME_MAP.put("Aves", "아베스");
        TEAM_NAME_MAP.put("Chaves", "샤베스");
        TEAM_NAME_MAP.put("Feirense", "페이렌스");
        TEAM_NAME_MAP.put("Penafiel", "페나피엘");
        TEAM_NAME_MAP.put("Academica", "아카데미카");
        TEAM_NAME_MAP.put("Beira-Mar", "베이라마르");
        TEAM_NAME_MAP.put("Leixoes", "레이소에스");
        TEAM_NAME_MAP.put("Varzim", "바르짐");
        TEAM_NAME_MAP.put("Farense", "파렌스");
        TEAM_NAME_MAP.put("Olhanense", "올랴넨스");
        TEAM_NAME_MAP.put("Portimonense", "포르티모넨스");
        TEAM_NAME_MAP.put("Estoril", "에스토릴");
        TEAM_NAME_MAP.put("Belenenses", "벨레넨스");
        TEAM_NAME_MAP.put("Casa Pia", "카사 피아");
        TEAM_NAME_MAP.put("Rio Ave", "히우 아브");
        TEAM_NAME_MAP.put("Famalicao", "파말리캉");
        TEAM_NAME_MAP.put("Gil Vicente", "질 비센트");
        TEAM_NAME_MAP.put("Santa Clara", "산타 클라라");
        TEAM_NAME_MAP.put("Pacos de Ferreira", "파수스 드 페헤이라");
        TEAM_NAME_MAP.put("Arouca", "아로우카");
        TEAM_NAME_MAP.put("Vizela", "비젤라");
        TEAM_NAME_MAP.put("Estrela da Amadora", "에스트렐라 다 아마도라");
        TEAM_NAME_MAP.put("Farense", "파렌스");
        TEAM_NAME_MAP.put("Portimonense", "포르티모넨스");
        TEAM_NAME_MAP.put("Estoril", "에스토릴");
        TEAM_NAME_MAP.put("Belenenses", "벨레넨스");
        TEAM_NAME_MAP.put("Casa Pia", "카사 피아");
        TEAM_NAME_MAP.put("Rio Ave", "히우 아브");
        TEAM_NAME_MAP.put("Famalicao", "파말리캉");
        TEAM_NAME_MAP.put("Gil Vicente", "질 비센트");
        TEAM_NAME_MAP.put("Santa Clara", "산타 클라라");
        TEAM_NAME_MAP.put("Pacos de Ferreira", "파수스");
        TEAM_NAME_MAP.put("Arouca", "아로우카");
        TEAM_NAME_MAP.put("Vizela", "비젤라");
        TEAM_NAME_MAP.put("Estrela da Amadora", "에스트렐라");
        
        // KBO 리그 팀명 매핑
        TEAM_NAME_MAP.put("Doosan Bears", "두산베어스");
        TEAM_NAME_MAP.put("Samsung Lions", "삼성라이온즈");
        TEAM_NAME_MAP.put("LG Twins", "LG트윈스");
        TEAM_NAME_MAP.put("KIA Tigers", "KIA타이거즈");
        TEAM_NAME_MAP.put("SSG Landers", "SSG랜더스");
        TEAM_NAME_MAP.put("Lotte Giants", "롯데자이언츠");
        TEAM_NAME_MAP.put("Kiwoom Heroes", "키움히어로즈");
        TEAM_NAME_MAP.put("NC Dinos", "NC다이노스");
        TEAM_NAME_MAP.put("KT Wiz", "KT위즈");
        TEAM_NAME_MAP.put("Hanwha Eagles", "한화이글스");
        TEAM_NAME_MAP.put("Sangmu Phoenix", "상무");
        TEAM_NAME_MAP.put("SK Wyverns", "SK와이번스");
        TEAM_NAME_MAP.put("Nexen Heroes", "넥센히어로즈");
        TEAM_NAME_MAP.put("MBC Blue Dragons", "MBC청룡");
        TEAM_NAME_MAP.put("OB Bears", "OB베어스");
        TEAM_NAME_MAP.put("Binggrae Eagles", "빙그레이글스");
        TEAM_NAME_MAP.put("Pacific Dolphins", "태평양돌핀스");
        TEAM_NAME_MAP.put("Sammi Superstars", "삼미슈퍼스타즈");
        TEAM_NAME_MAP.put("Chungbo Pintos", "청보핀토스");
        TEAM_NAME_MAP.put("Haitai Tigers", "해태타이거즈");
        TEAM_NAME_MAP.put("Lotte Orions", "롯데오리온스");
        TEAM_NAME_MAP.put("Samsung Lions", "삼성라이온즈");
        TEAM_NAME_MAP.put("MBC Blue Dragons", "MBC청룡");
        TEAM_NAME_MAP.put("OB Bears", "OB베어스");
        TEAM_NAME_MAP.put("Binggrae Eagles", "빙그레이글스");
        TEAM_NAME_MAP.put("Pacific Dolphins", "태평양돌핀스");
        TEAM_NAME_MAP.put("Sammi Superstars", "삼미슈퍼스타즈");
        TEAM_NAME_MAP.put("Chungbo Pintos", "청보핀토스");
        TEAM_NAME_MAP.put("Haitai Tigers", "해태타이거즈");
        TEAM_NAME_MAP.put("Lotte Orions", "롯데오리온스");
        
        // UFC 선수명 매핑
        TEAM_NAME_MAP.put("Jon Jones", "존 존스");
        TEAM_NAME_MAP.put("Israel Adesanya", "이스라엘 아데산야");
        TEAM_NAME_MAP.put("Alex Pereira", "알렉스 페레이라");
        TEAM_NAME_MAP.put("Sean Strickland", "션 스트리클랜드");
        TEAM_NAME_MAP.put("Dricus Du Plessis", "드리커스 뒤 플레시스");
        TEAM_NAME_MAP.put("Robert Whittaker", "로버트 휘태커");
        TEAM_NAME_MAP.put("Marvin Vettori", "마빈 베토리");
        TEAM_NAME_MAP.put("Jared Cannonier", "재러드 캐노니어");
        TEAM_NAME_MAP.put("Paulo Costa", "파울로 코스타");
        TEAM_NAME_MAP.put("Derek Brunson", "데릭 브런슨");
        TEAM_NAME_MAP.put("Jack Hermansson", "잭 허만슨");
        TEAM_NAME_MAP.put("Darren Till", "대런 틸");
        TEAM_NAME_MAP.put("Kelvin Gastelum", "켈빈 가스텔럼");
        TEAM_NAME_MAP.put("Uriah Hall", "우리아 홀");
        TEAM_NAME_MAP.put("Brad Tavares", "브래드 타바레스");
        TEAM_NAME_MAP.put("Andre Muniz", "안드레 무니즈");
        TEAM_NAME_MAP.put("Brendan Allen", "브렌던 앨런");
        TEAM_NAME_MAP.put("Nassourdine Imavov", "나수르딘 이마보프");
        TEAM_NAME_MAP.put("Roman Dolidze", "로만 돌리제");
        TEAM_NAME_MAP.put("Anthony Hernandez", "앤서니 에르난데스");
        TEAM_NAME_MAP.put("Chris Curtis", "크리스 커티스");
        TEAM_NAME_MAP.put("Marc-Andre Barriault", "마크-앙드레 바리오");
        TEAM_NAME_MAP.put("Edmen Shahbazyan", "에드멘 샤바지안");
        TEAM_NAME_MAP.put("Punahele Soriano", "푸나헬레 소리아노");
        TEAM_NAME_MAP.put("Rodolfo Vieira", "로돌포 비에이라");
        TEAM_NAME_MAP.put("Gerald Meerschaert", "제럴드 미어샤트");
        TEAM_NAME_MAP.put("Joe Pyfer", "조 파이퍼");
        TEAM_NAME_MAP.put("Abusupiyan Magomedov", "아부수피얀 마고메도프");
        TEAM_NAME_MAP.put("Andre Petroski", "안드레 페트로스키");
        TEAM_NAME_MAP.put("Wellington Turman", "웰링턴 투르만");
        TEAM_NAME_MAP.put("Jacob Malkoun", "제이콥 말쿤");
        TEAM_NAME_MAP.put("Phil Hawes", "필 호스");
        TEAM_NAME_MAP.put("Makhmud Muradov", "막무드 무라도프");
        TEAM_NAME_MAP.put("Aliaskhab Khizriev", "알리아스카브 키즈리에프");
        TEAM_NAME_MAP.put("Albert Duraev", "알베르트 두라예프");
        TEAM_NAME_MAP.put("Caio Borralho", "카이오 보랄호");
        TEAM_NAME_MAP.put("Gregory Rodrigues", "그레고리 로드리게스");
        TEAM_NAME_MAP.put("Bruno Silva", "브루노 실바");
        TEAM_NAME_MAP.put("Jordan Wright", "조던 라이트");
        TEAM_NAME_MAP.put("Tresean Gore", "트레션 고어");
        TEAM_NAME_MAP.put("Josh Fremd", "조시 프렘드");
        TEAM_NAME_MAP.put("Cody Brundage", "코디 브런디지");
        TEAM_NAME_MAP.put("Dalcha Lungiambula", "달차 룽기암불라");
        TEAM_NAME_MAP.put("Denis Tiuliulin", "데니스 티울린");
        TEAM_NAME_MAP.put("Park Jun-yong", "박준용");
        
        // 추가 UEFA 챔피언스 리그 팀명 매핑 (다양한 표기법 포함)
        TEAM_NAME_MAP.put("FC Copenhagen", "코펜하겐");
        TEAM_NAME_MAP.put("Copenhagen", "코펜하겐");
        TEAM_NAME_MAP.put("København", "코펜하겐");
        TEAM_NAME_MAP.put("Malmö", "말뫼");
        TEAM_NAME_MAP.put("Malmo FF", "말뫼");
        TEAM_NAME_MAP.put("Malmo", "말뫼");
        TEAM_NAME_MAP.put("Feyenoord Rotterdam", "페예노르트");
        TEAM_NAME_MAP.put("Salzburg", "잘츠부르크");
        TEAM_NAME_MAP.put("RB Salzburg", "잘츠부르크");
        TEAM_NAME_MAP.put("Crvena Zvezda", "츠르베나 즈베즈다");
        TEAM_NAME_MAP.put("Crvena Zvezda Belgrade", "츠르베나 즈베즈다");
        TEAM_NAME_MAP.put("GNK Dinamo Zagreb", "디나모 자그레브");
        TEAM_NAME_MAP.put("FC Shakhtar Donetsk", "샤흐타르 도네츠크");
        TEAM_NAME_MAP.put("FC Dynamo Kyiv", "디나모 키예프");
        TEAM_NAME_MAP.put("SK Slavia Prague", "슬라비아 프라하");
        TEAM_NAME_MAP.put("AC Sparta Prague", "스파르타 프라하");
        TEAM_NAME_MAP.put("BSC Young Boys", "영 보이즈");
        TEAM_NAME_MAP.put("FC Basel", "바젤");
        TEAM_NAME_MAP.put("Rosenborg BK", "로젠보르그");
        TEAM_NAME_MAP.put("Molde FK", "몰데");
        TEAM_NAME_MAP.put("FK Bodø/Glimt", "보되/글림트");
        TEAM_NAME_MAP.put("PFC Ludogorets Razgrad", "루도고레츠");
        TEAM_NAME_MAP.put("Ferencvárosi TC", "페렌츠바로시");
        TEAM_NAME_MAP.put("Qarabağ FK", "카라바흐");
        TEAM_NAME_MAP.put("FC Sheriff Tiraspol", "셰리프 티라스폴");
        TEAM_NAME_MAP.put("FC Astana", "아스타나");
        TEAM_NAME_MAP.put("APOEL FC", "아포엘");
        TEAM_NAME_MAP.put("Olympiacos FC", "올림피아코스");
        TEAM_NAME_MAP.put("PAOK FC", "PAOK");
        TEAM_NAME_MAP.put("AEK Athens FC", "AEK 아테네");
        TEAM_NAME_MAP.put("Panathinaikos FC", "파나티나이코스");
        TEAM_NAME_MAP.put("Galatasaray SK", "갈라타사라이");
        TEAM_NAME_MAP.put("Fenerbahçe SK", "페네르바흐체");
        TEAM_NAME_MAP.put("Beşiktaş JK", "베식타시");
        TEAM_NAME_MAP.put("Trabzonspor Kulübü", "트라브존스포르");
        TEAM_NAME_MAP.put("FC Zenit Saint Petersburg", "제니트");
        TEAM_NAME_MAP.put("Zenit", "제니트");
        TEAM_NAME_MAP.put("PFC CSKA Moscow", "CSKA 모스크바");
        TEAM_NAME_MAP.put("FC Spartak Moscow", "스파르타크 모스크바");
        TEAM_NAME_MAP.put("FC Lokomotiv Moscow", "로코모티프 모스크바");
        TEAM_NAME_MAP.put("FC Krasnodar", "크라스노다르");
        TEAM_NAME_MAP.put("FC Rostov", "로스토프");
        TEAM_NAME_MAP.put("FC Rubin Kazan", "루빈 카잔");
        TEAM_NAME_MAP.put("FC Ufa", "우파");
        TEAM_NAME_MAP.put("FC Akhmat Grozny", "아흐마트 그로즈니");
        TEAM_NAME_MAP.put("FC Ural Yekaterinburg", "우랄 예카테린부르크");
        TEAM_NAME_MAP.put("FC Sochi", "소치");
        TEAM_NAME_MAP.put("FC Khimki", "힘키");
        TEAM_NAME_MAP.put("FC Tambov", "탐보프");
        TEAM_NAME_MAP.put("FC Orenburg", "오렌부르크");
        TEAM_NAME_MAP.put("FC Krylia Sovetov", "크릴리야 소베토프");
        TEAM_NAME_MAP.put("FC Nizhny Novgorod", "니즈니 노브고로드");
        TEAM_NAME_MAP.put("FC Torpedo Moscow", "토르페도 모스크바");
        TEAM_NAME_MAP.put("FC Fakel Voronezh", "파켈 보로네시");
        TEAM_NAME_MAP.put("KuPS", "카라이트 알마티");

        TEAM_NAME_MAP.put("FC Baltika Kaliningrad", "발티카 칼리닌그라드");
        TEAM_NAME_MAP.put("AFC Ajax", "아약스");
        TEAM_NAME_MAP.put("FC Porto", "포르투");
        TEAM_NAME_MAP.put("SL Benfica", "벤피카");
        TEAM_NAME_MAP.put("Sporting Clube de Portugal", "스포르팅 CP");
        TEAM_NAME_MAP.put("SC Braga", "브라가");
        TEAM_NAME_MAP.put("Vitória SC", "비토리아 기마랑이스");
        TEAM_NAME_MAP.put("Boavista FC", "보아비스타");
        TEAM_NAME_MAP.put("CS Marítimo", "마리티무");
        TEAM_NAME_MAP.put("CD Nacional", "나시오날");
        TEAM_NAME_MAP.put("Moreirense FC", "모레이렌스");
        TEAM_NAME_MAP.put("CD Tondela", "톤델라");
        TEAM_NAME_MAP.put("CD Aves", "아베스");
        TEAM_NAME_MAP.put("GD Chaves", "샤베스");
        TEAM_NAME_MAP.put("CD Feirense", "페이렌스");
        TEAM_NAME_MAP.put("FC Penafiel", "페나피엘");
        TEAM_NAME_MAP.put("Associação Académica de Coimbra", "아카데미카");
        TEAM_NAME_MAP.put("SC Beira-Mar", "베이라마르");
        TEAM_NAME_MAP.put("Leixões SC", "레이소에스");
        TEAM_NAME_MAP.put("Varzim SC", "바르짐");
        TEAM_NAME_MAP.put("SC Farense", "파렌스");
        TEAM_NAME_MAP.put("SC Olhanense", "올랴넨스");
        TEAM_NAME_MAP.put("Portimonense SC", "포르티모넨스");
        TEAM_NAME_MAP.put("GD Estoril Praia", "에스토릴");
        TEAM_NAME_MAP.put("CF Os Belenenses", "벨레넨스");
        TEAM_NAME_MAP.put("Casa Pia AC", "카사 피아");
        TEAM_NAME_MAP.put("Rio Ave FC", "히우 아브");
        TEAM_NAME_MAP.put("FC Famalicão", "파말리캉");
        TEAM_NAME_MAP.put("Gil Vicente FC", "질 비센트");
        TEAM_NAME_MAP.put("CD Santa Clara", "산타 클라라");
        TEAM_NAME_MAP.put("FC Paços de Ferreira", "파수스 드 페헤이라");
        TEAM_NAME_MAP.put("FC Arouca", "아로우카");
        TEAM_NAME_MAP.put("FC Vizela", "비젤라");
        TEAM_NAME_MAP.put("CF Estrela da Amadora", "에스트렐라 다 아마도라");
        
    

    }
    
    /**
     * 팀명을 한국 통칭명으로 변환
     * @param originalName 원본 팀명
     * @return 한국 통칭명 (매핑이 없으면 원본 반환)
     */
    public static String getKoreanName(String originalName) {
        if (originalName == null || originalName.trim().isEmpty()) {
            return originalName;
        }
        
        String koreanName = TEAM_NAME_MAP.get(originalName.trim());
        return koreanName != null ? koreanName : originalName;
    }


    public static String getEnglishName(String koreanName) {
        System.out.println("Name: "+koreanName);
        if (koreanName == null || koreanName.trim().isEmpty()) {
            return koreanName;
        }

        // value(=한글명) → key(=영문명) 검색
        for (Map.Entry<String, String> entry : TEAM_NAME_MAP.entrySet()) {
            if (entry.getValue().equals(koreanName.trim())) {
                return entry.getKey(); // 영문 팀명 반환
            }
        }

        return koreanName; // 매핑 없으면 그대로 반환
    }

    
    /**
     * 팀명 매핑에 새로운 팀 추가
     * @param originalName 원본 팀명
     * @param koreanName 한국 통칭명
     */
    public static void addTeamMapping(String originalName, String koreanName) {
        TEAM_NAME_MAP.put(originalName, koreanName);
    }
}
