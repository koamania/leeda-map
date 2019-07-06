# leeda-map 어플리케이션
## 개요
- 지도 검색 어플리케이션

## 로그인 정보
- id : iwantkakao@daum.net
- pw : 123123!

## 실행 방법
1. [jar 파일 다운로드](https://github.com/blackstoneroad/leeda-map/releases/download/0.0.1-SNAPSHOT/leeda-map.jar)
2. 다운로드 받은 경로에서 커맨드 실행
    - `java -jar leeda-map.jar`
3. http://localhost:8080 접속
    
## 참고 사항
### redis 관련  
embedded redis를 사용하게되므로 6379 포트를 이미 사용중인 경우 다른 포트로 변경해서 시작하시길 바랍니다.  
- ex) `java -jar leeda-map.jar --spring.redis.port=26379`

### http port 관련
spring boot 기본 포트로 8080을 사용합니다. 이미 사용중인 경우 다른 포트로 변경해서 시작하기실 바랍니다.
- ex) `java -jar -Dserver.port=8090 leeda-map.jar`

### h2 console 접속
- http://localhost:8080/h2-console/login.jsp
- id : dhlee
- pw : 11qqww22!!

## 기능 설명
### 로그인
- 사용자 ID, PW를 이용해 로그인 가능
- 초기 어플리케이션 시작시에 샘플 사용자 추가(상기 로그인 정보 확인 바람)
- 패스워드 암호화(PBKDF2를 활용한 password 및 salt hashing)
### 장소 검색
- 키워드를 활용한 장소 검색 기능
- 검색 결과 하단에 더보기 버튼으로 pagination
- kakao api를 활용했으나 추후 다른 api로도 확장 가능
- 현재 위치 정보를 기준으로 검색(브라우저 허용시)
### 검색된 장소의 상세 조회
- 장소에 대한 카카오 맵의 상세 정보 페이지 링크 연결
- 검색 리스트에서 도로명 주소 및 지번 주소, 전화번호 확인 가능(카카오 맵으로 링크 연결)
### 내 검색 히스토리
- 검색 히스토리 버튼 클릭 시 최신순으로 나열된 검색 히스토리
- 키워드 클릭시 바로 다시 검색 가능
### 인기 키워드 목록
- 많이 검색된 키워드와 카운트롤 10개까지 보여줌

## 개발 환경
- JDK 8
- Gradle 4.10.2
- Spring boot 2.1.6

## 브라우저 테스트
- Chrome
- Edge
- Firefox
- Safari

## 오픈소스 라이센스 명시
- [Spring Framework](https://spring.io/)
    - 스프링 부트 기반 어플리케이션 개발
- [PowerMock](https://github.com/powermock/powermock)
    - 테스트 mocking 목적
- [Lombok](https://projectlombok.org/)
    - boilerplate code 자동 생성 목적
- [Hibernate](https://hibernate.org/)
    - JPA 사용을 위한 프레임워크
- [Embedded Redis](https://github.com/ozimov/embedded-redis)
    - session clustering 및 local test 환경 구축을 위한 embedded redis
- [H2 Database](https://www.h2database.com/html/main.html)
    - embedded db 활용을 위한 데이터베이스
- [Semantic UI](https://semantic-ui.com)
    - view 구성을 위한 javascript framework
    
