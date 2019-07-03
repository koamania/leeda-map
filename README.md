# leeda-map 어플리케이션
## 개요
- 지도 검색 어플리케이션
## 로그인 정보
- id : iwantkakao@daum.net
- pw : 123123!
## 실행 방법
1. [jar 파일 다운로드](https://github.com/blackstoneroad/leeda-map/releases/download/0.0.1-SNAPSHOT/map-0.0.1-SNAPSHOT.jar)
2. 다운로드 받은 경로에서 커맨드 실행
    - `java -jar map-0.0.1-SNAPSHOT.jar`
## 참고 사항
embedded redis를 사용하게되므로 6379 포트를 이미 사용중인 경우 다른 포트로 변경해서 시작하시길 바랍니다.  
 - ex) `java -jar map-0.0.1-SNAPSHOT.jar --spring.redis.port=26379`
## 개발 환경
- JDK 8
- Gradle 4.10.2
- Spring boot 2.1.6
 
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
    
